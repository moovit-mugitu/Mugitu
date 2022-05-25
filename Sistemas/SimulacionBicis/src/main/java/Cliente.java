import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
                linea = id + "/" + electrica;
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

    public static class BiciConsumer extends DefaultConsumer {

        public BiciConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                throws IOException {
            long biciId = new BigInteger(body).longValue();
            System.out.println("bici id: " + biciId);
            if (biciId >= 0) {
                Random r = new Random();
                int userId = r.nextInt(4) + 4;

                String token = loggearseConUser(userId);
                if(token != null)crearRequest(biciId, userId, token);
            }
        }

        private void crearRequest(long biciId, long userId, String token) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("http://localhost:8000/MUgitu/REST/api/utilizar/create/" + biciId + "/" + userId))
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer "+token)
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if(response.statusCode() == 200){
                    JSONObject json = new JSONObject(response.body());
                    System.out.println("Utilizacion creada, ID: "+json.get("utilizaId"));
                }
                else{
                    System.out.println("Request error: "+response.statusCode());
                }
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        private String loggearseConUser(long userId) throws UnsupportedEncodingException {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("http://localhost:8000/MUgitu/REST/api/login"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(createBodyForLogin(userId)))
                    .build();

            // use the client to send the request
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if(response.statusCode() == 200){
                    JSONObject json = new JSONObject(response.body());
                    return (String) json.get("accessToken");
                }
                else{
                    System.out.println("Request error: "+response.statusCode());
                }
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private byte[] createBodyForLogin(long userId) throws UnsupportedEncodingException {
            Map<String,String> arguments = new HashMap<>();
            arguments.put("username", "user"+userId+"@user");
            arguments.put("password", "user");
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            return sj.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    public static class ConsumerDeadLetter extends DefaultConsumer {

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

