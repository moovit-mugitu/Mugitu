package Mooving.MUgituApi.rabbitMQ;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.estacion.EstacionDao;
import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.dao.evento.EventoDao;
import Mooving.MUgituApi.dao.utilizacion.UtilizacionDao;
import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Estacion;
import Mooving.MUgituApi.entities.Estacionar;
import Mooving.MUgituApi.entities.Evento;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class RabbitMQConsumerProducer {

    private final AmqpTemplate rabbitTemplate;
    private final EstacionarDao estacionarDao;
    private final EstacionDao estacionDao;
    private final BiciDao biciDao;
    private final EventoDao eventoDao;
    private final UtilizacionDao utilizacionDao;

    public RabbitMQConsumerProducer(AmqpTemplate rabbitTemplate, EstacionarDao estacionarDao, EstacionDao estacionDao, BiciDao biciDao, EventoDao eventoDao, UtilizacionDao utilizacionDao) {
        this.rabbitTemplate = rabbitTemplate;
        this.estacionarDao = estacionarDao;
        this.estacionDao = estacionDao;
        this.biciDao = biciDao;
        this.eventoDao = eventoDao;
        this.utilizacionDao = utilizacionDao;
    }

    @RabbitListener(queues = RabbitMQConfiguration.ESTACION_QUEUE)
    public void receiveMessageEstacion(@Payload String message) throws AmqpRejectAndDontRequeueException{
        long estacionId = Long.parseLong(message.split("/")[0]);
        boolean electrica = Boolean.parseBoolean(message.split("/")[1]);
        System.out.println("Recieved Message From RabbitMQ: " + message);
        //Consultar bicis
        long biciId = consultarBici(estacionId, electrica);
        //Enviar resultado
        try{
            enviarBici(biciId, estacionId);
        }catch (AmqpRejectAndDontRequeueException e){
            throw e;
        }
    }

    private long consultarBici(long estacionId, boolean electrica) {
        List<Estacionar> estacionars = estacionarDao.getEstacionarSinFechaFinByEstacion(estacionId);
        List<Bici> bicis = estacionars.stream().map(Estacionar::getBici)
                .filter(line -> line.getElectrica() == electrica)
                .collect(Collectors.toList());
        if(bicis.size()==0) return -1L;
        Random r  = new Random();
        int pos = r.nextInt(bicis.size());
        return bicis.get(pos).getBiciId();
    }

    private void enviarBici(long biciId, long estacionId){
        if(biciId != -1){
            rabbitTemplate.convertAndSend(
                    RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.ROUTING_KEY_BICI, estacionId+"/"+biciId);
        }else{
            throw new AmqpRejectAndDontRequeueException("utilizar/"+estacionId, true, null);
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.EVENTO_QUEUE)
    public void receiveMessageEvento(@Payload String message) throws AmqpRejectAndDontRequeueException{
        // bici/estado/latitud/longitud
        String[] messages = message.split("/");
        System.out.println("Recieved Message From RabbitMQ: " + message);
        //Obtener Bici
        try{
            Bici bici = biciDao.getBici(Long.parseLong(messages[0]));
            Evento evento = new Evento();
            evento.setBici(bici);
            evento.setEstado(Boolean.parseBoolean(messages[1]));
            evento.setLatitud(Double.valueOf(messages[2]));
            evento.setLongitud(Double.valueOf(messages[3]));
            evento.setFecha(new Date());
            eventoDao.addEvento(evento);
        }catch (Exception e){
            throw new AmqpRejectAndDontRequeueException("evento/"+message, true, null);
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.ESTACIONAR_QUEUE)
    public void receiveMessageEstacionar(@Payload String message) throws AmqpRejectAndDontRequeueException{
        //  bici/estacion
        String[] messages = message.split("/");
        System.out.println("Recieved Message From RabbitMQ: " + message);
        try{
            long biciId = Long.parseLong(messages[0]);
            long estacionId = Long.parseLong(messages[1]);
            long userId = Long.parseLong(messages[2]);
            //Obtener bici y estacion
            Bici bici = biciDao.getBici(biciId);
            Estacion estacion = estacionDao.getEstacion(estacionId);
            Estacionar estacionar = new Estacionar();
            estacionar.setBici(bici);
            estacionar.setEstacion(estacion);
            estacionar.setFechaInicio(new Date());
            estacionarDao.addEstacionar(estacionar);
            utilizacionDao.finishUtilizacion(biciId, userId);
        }catch (Exception e){
            throw new AmqpRejectAndDontRequeueException("estacionar/"+message, true, null);
        }
    }

}