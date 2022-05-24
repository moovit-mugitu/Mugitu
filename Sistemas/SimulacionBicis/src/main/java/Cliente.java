import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Cliente {
    public final static String EXCHANGE_NAME = "exchangeApiSimulator";
    public final static String DLX_NAME = "dlxApiSimulator";

    public final static String ESTACION_QUEUE = "queueEstacion";
    public final static String BICI_QUEUE = "queueBici";
    public final static String DLQ_NAME = "queueDeadletter";

    public final static String ROUTING_KEY_ESTACION = "estacionId";
    public final static String ROUTING_KEY_BICI = "biciId";

    ConnectionFactory factory;
    Random generador;
    Channel channel;

    public Cliente() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //puerto 15672 o 15673 para TLS
        generador = new Random();
    }

    public void enviarMensaje() {
        try (Connection connection = factory.newConnection()) {
            //Crear exchanges
            channel = connection.createChannel();
            channel.queueBind(DLQ_NAME, DLX_NAME, "");
            channel.queueBind(BICI_QUEUE, EXCHANGE_NAME, ROUTING_KEY_BICI);
            //Crear consumidores
            Consumer consumerDeadLetter = new ConsumerDeadLetter(channel);
            channel.basicConsume(DLQ_NAME, true, consumerDeadLetter);
            Consumer consumerBiciId = new BiciConsumer(channel);
            channel.basicConsume(BICI_QUEUE, true, consumerBiciId);

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
                    linea = 1+"/"+electrica;
                    if (!this.isInterrupted()) {

                        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_ESTACION, null, linea.getBytes());
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
                Random r = new Random();
                int userId = r.nextInt(4)+3;
                URL url = new URL("http://localhost:8000/MUgitu/REST/api/utilizar/create/"+biciId+"/"+userId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");

                connection.connect();
                System.out.println(connection.getResponseCode());
                connection.disconnect();
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

