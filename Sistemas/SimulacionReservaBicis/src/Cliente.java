import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Cliente {
    final static String EXCHANGE_NAME = "solicitudEstacion";
    final static String DLX_NAME = "MiDeadLetterExchange";

    ConnectionFactory factory;
    Random generador;
    Channel channel;

    public Cliente() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //puerto 5672 o 5673 para TLS
        generador = new Random();
    }

    public void enviarMensaje() {
        try (Connection connection = factory.newConnection()) {
            //Crear exchanges
            channel = connection.createChannel();
            channel.exchangeDeclare(DLX_NAME, "fanout");
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            //Crear cola DLX y de lextura
            String colaDLX = channel.queueDeclare().getQueue();
            String colaBici = channel.queueDeclare().getQueue();
            channel.queueBind(colaDLX, DLX_NAME, "");
            channel.queueBind(colaBici, EXCHANGE_NAME, "biciId");
            //Crear consumidores
            Consumer consumerDeadLetter = new ConsumerDeadLetter(channel);
            channel.basicConsume(colaDLX, true, consumerDeadLetter);
            Consumer consumerBiciId = new BiciConsumer(channel);
            channel.basicConsume(colaBici, true, consumerBiciId);

            Publisher publisher = new Publisher();
            publisher.start();

            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publisher.interrupt();
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();

        }
    }

    public class Publisher extends Thread {

        @Override
        public void run() {
            String linea;
            int id;
            boolean electrica;
            Random generador = new Random();
            try {
                while (!this.isInterrupted()) {
                    id = generador.nextInt(264) + 1;
                    electrica = generador.nextBoolean();
                    linea = id+"/"+electrica;
                    if (!this.isInterrupted()) {

                        channel.basicPublish(EXCHANGE_NAME, "estacionId", null, linea.getBytes());
                        System.out.println(" Enviado: " + linea);
                        Thread.sleep(1000);
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("hilo interrumpido");
            }
        }
    }

    public class BiciConsumer extends DefaultConsumer{

        public BiciConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                throws IOException {
            String message = new String(body, "UTF-8");
            int biciId = Integer.parseInt(message);
            System.out.println("bici id: "+biciId);
            if(biciId >= 0){
                //HTTP REQUEST PARA HACER LA RESERVA
            }
            else{ //Devuelve -1
                //No hay disponibles
            }
        }
    }

    public class ConsumerDeadLetter extends DefaultConsumer {

        public ConsumerDeadLetter(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                throws IOException {
            String message = new String(body, "UTF-8");
            System.out.println("valor rechazado: " + message);
        }
    }

    public synchronized void stop() {
        this.notify();
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Cliente cliente = new Cliente();
        System.out.println(" Esperando mensaje. Pulsa return para terminar");
        Thread hiloEspera = new Thread(() -> {
            teclado.nextLine();
            cliente.stop();
        });
        hiloEspera.start();
        cliente.enviarMensaje();
        teclado.close();
    }
}

