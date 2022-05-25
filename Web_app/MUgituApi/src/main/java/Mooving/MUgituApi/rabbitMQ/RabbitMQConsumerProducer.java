package Mooving.MUgituApi.rabbitMQ;

import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Estacionar;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class RabbitMQConsumerProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private EstacionarDao estacionarDao;

    @RabbitListener(queues = RabbitMQConfiguration.ESTACION_QUEUE)
    public void recievedMessage(@Payload String message) throws AmqpRejectAndDontRequeueException{
        long estacionId = Long.parseLong(message.split("/")[0]);
        boolean electrica = Boolean.parseBoolean(message.split("/")[1]);
        System.out.println("Recieved Message From RabbitMQ: " + message);
        //Consultar bicis
        long biciId = consultarBici(estacionId, electrica);
        //Enviar resultado
        try{
            enviarMensaje(biciId, estacionId);
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

    private void enviarMensaje(long biciId, long estacionId){
        if(biciId != -1){
            rabbitTemplate.convertAndSend(
                    RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.ROUTING_KEY_BICI, biciId);
        }else{
            throw new AmqpRejectAndDontRequeueException(estacionId+"/"+biciId, true, null);
        }
    }
}