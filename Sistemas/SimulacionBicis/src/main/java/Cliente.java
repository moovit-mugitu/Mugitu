import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Cliente {
    public final static String EXCHANGE_NAME = "exchangeApiSimulator";
    public final static String DLX_NAME = "dlxApiSimulator";
    public final static String BICI_QUEUE = "queueBici";
    public final static String DLQ_NAME = "queueDeadletter";
    public final static String ROUTING_KEY_ESTACION = "estacionId";
    public final static String ROUTING_KEY_BICI = "biciId";
    public final static String ROUTING_KEY_EVENTO = "eventoId";
    public final static String ROUTING_KEY_ESTACIONAR = "estacionarId";

    public final static int NUM_BICIS_CREAR = 5;
    public static int NUM_BICIS_CREADAS;

    ConnectionFactory factory;
    Random generador;
    static Channel channel;
    static ExecutorService executor;

    public Cliente() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //puerto 15672 o 15673 para TLS
        generador = new Random();
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void iniciar() {
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

    public static class Publisher extends Thread {
        @Override
        public void run() {
            String linea;
            int id;
            boolean electrica;
            Random generador = new Random();
            try {
                for (NUM_BICIS_CREADAS = 0; NUM_BICIS_CREADAS < NUM_BICIS_CREAR; NUM_BICIS_CREADAS++) {
                    id = generador.nextInt(264) + 1;
                    electrica = generador.nextBoolean();
                    linea = id + "/" + electrica;
                    if (!this.isInterrupted()) {
                        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_ESTACION, null, linea.getBytes());
                        Thread.sleep(1000);
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("hilo interrumpido");
            }
            System.out.println("Acaba hilo publisher");
        }
    }

    public static class BiciConsumer extends DefaultConsumer {

        public BiciConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) {
            //LEER MENSAJE
            String message = new String(body, StandardCharsets.UTF_8);
            String[] messages = message.split("/");
            long biciId = new BigInteger(messages[1]).longValue();
            long estacionId = new BigInteger(messages[0]).longValue();

            //LOGGEARSE PARA CREAR UTILIZACION
            if (biciId >= 0) {
                Random r = new Random();
                int userId = r.nextInt(4) + 4;
                String token = loggearseConUser(userId);

                //CREAR UTILIZACION
                if (token != null) {
                    boolean creada = crearRequest(biciId, userId, token);
                    if (creada) {
                        //AÑADIR A EXECUTOR Y SI ES EL ULTIMO ESPERAR
                        executor.execute(new SimularMovimiento(biciId, estacionId, userId, channel));
                        if (NUM_BICIS_CREADAS+1 == NUM_BICIS_CREAR) {
                            executor.shutdown();
                            try {
                                executor.awaitTermination(200, TimeUnit.SECONDS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        private boolean crearRequest(long biciId, long userId, String token) {
            HttpClient client = HttpClient.newHttpClient();
            boolean creada = false;
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("http://localhost:8000/MUgitu/REST/api/utilizar/create/" + biciId + "/" + userId))
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200 || response.statusCode() == 201) {
                    JSONObject json = new JSONObject(response.body());
                    System.out.println("Utilizacion creada, ID: " + json.get("utilizaId") + " bici: " + biciId);
                    creada = true;
                } else {
                    System.out.println("Request error: " + response.statusCode() + " Bici: " + biciId);
                }
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            return creada;
        }

        private String loggearseConUser(long userId) {
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
                if (response.statusCode() == 200) {
                    JSONObject json = new JSONObject(response.body());
                    return (String) json.get("accessToken");
                } else {
                    System.out.println("Request error: " + response.statusCode());
                }
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private byte[] createBodyForLogin(long userId) {
            Map<String, String> arguments = new HashMap<>();
            arguments.put("username", "user" + userId + "@user");
            arguments.put("password", "user");
            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            return sj.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    public static class ConsumerDeadLetter extends DefaultConsumer {
        Map<String, Integer> reintentoMensajes;

        public ConsumerDeadLetter(Channel channel) {
            super(channel);
            reintentoMensajes = new HashMap<>();
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                throws IOException {
            String message = new String(body, StandardCharsets.UTF_8);
            System.out.println("valor rechazado: " + message);
            String[] messages = message.split("/");
            message = message.replace("$", "");

            //Maximo 5 reenvios por mensaje rechazado
            if (reintentoMensajes.containsKey(message)) {
                if (reintentoMensajes.get(message) > 5) {
                    System.out.println("Limite maximo de intentos superado " + "\n");
                    return;
                }
                reintentoMensajes.put(message, reintentoMensajes.get(message) + 1);
            } else {
                reintentoMensajes.put(message, 1);
            }

            switch (messages.length) {
                case 2:
                    System.out.println("La estacion seleccionada no tiene el tipo de bici solicitado" + "\n");
                    break;
                case 4:
                    System.out.println("Reenvio: " + message + "\n");
                    channel.basicPublish(Cliente.EXCHANGE_NAME, Cliente.ROUTING_KEY_EVENTO, null, message.getBytes(StandardCharsets.UTF_8));
                    break;
                case 3:
                    System.out.println("Reenvio: " + message + "\n");
                    channel.basicPublish(Cliente.EXCHANGE_NAME, Cliente.ROUTING_KEY_ESTACIONAR, null, message.getBytes(StandardCharsets.UTF_8));
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.iniciar();
    }
}

