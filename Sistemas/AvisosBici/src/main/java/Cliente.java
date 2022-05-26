import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class Cliente {
    final static String NECESIDADES_EXCHANGE = "necesidadesBicis";
    final static String SOLUCIONES_EXCHANGE = "solucionesBicis";

    ConnectionFactory factory;
    Random generador;
    Channel channel;

    Monitorizacion monitorizacion;

    public Cliente() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //puerto 5672 o 5673 para TLS
        generador = new Random();
    }

    public Map<String, List<Double>> conseguirbicis() throws IOException, InterruptedException, JSONException {
        Map<String, List<Double>> map = new HashMap<>();
        String token = loggearseConUser();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("http://localhost:8000/MUgitu/REST/api/ia/biciEstacion"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject json = new JSONObject(response.body());
            return map = new Gson().fromJson(json.toString(), HashMap.class);
        } else {
            System.out.println("Request error: " + response.statusCode());
        }
        return map;
    }

    private String loggearseConUser() throws UnsupportedEncodingException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("http://localhost:8000/MUgitu/REST/api/login"))
                .header("accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofByteArray(createBodyForLogin()))
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

    private byte[] createBodyForLogin() throws UnsupportedEncodingException {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("username", "user@user");
        arguments.put("password", "user");
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        return sj.toString().getBytes(StandardCharsets.UTF_8);
    }

    public void enviarMensaje() {
        try (Connection connection = factory.newConnection()) {

            channel = connection.createChannel();
            channel.exchangeDeclare(NECESIDADES_EXCHANGE, "fanout", true);
            channel.exchangeDeclare(SOLUCIONES_EXCHANGE, "fanout", true);

            monitorizacion = new Monitorizacion(this);
            monitorizacion.start();
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();

        }
    }

    public class Monitorizacion extends Thread {
        Cliente cliente;

        public Monitorizacion(Cliente cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            Map<String, List<Double>> map;
            List<String> estacionesFalta;
            try {
                while (!this.isInterrupted()) {
                    map = cliente.conseguirbicis();
                    //FILTAR MAPA Y ESTACIONES SOLUCION
                    estacionesFalta = map.entrySet().stream()
                            .filter(e -> e.getValue().get(0) / e.getValue().get(1) <= 0.35)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());
                    List<String> mejoresEstaciones = getMejoresEstaciones(map,
                            Math.min(estacionesFalta.size(), map.size() - estacionesFalta.size()));

                    //CREAR MENSAJE
                    StringBuilder buildFalta = new StringBuilder();
                    estacionesFalta.forEach(s -> {
                        buildFalta.append(s).append("/");
                    });
                    StringBuilder buildSolucion = new StringBuilder();
                    mejoresEstaciones.forEach(s -> {
                        buildSolucion.append(s).append("/");
                    });
                    String lineaNecesidad = (buildFalta.toString().equals("")) ? "Ninguna" : buildFalta.substring(0, buildFalta.length() - 1);
                    String lineaSolucion = (buildSolucion.toString().equals("")) ? "Ninguna" : buildSolucion.substring(0, buildSolucion.length() - 1);

                    //ENVIAR MENSAJE
                    if (!this.isInterrupted()) {
                        channel.basicPublish(NECESIDADES_EXCHANGE, "", null, lineaNecesidad.getBytes());
                        System.out.println(" Enviado: " + lineaNecesidad);

                        channel.basicPublish(SOLUCIONES_EXCHANGE, "", null, lineaSolucion.getBytes());
                        System.out.println(" Enviado: " + lineaNecesidad);
                        Thread.sleep(10000);
                    }
                }
            } catch (IOException | InterruptedException | JSONException e) {
                System.out.println("hilo interrumpido");
            }
        }

        private List<String> getMejoresEstaciones(Map<String, List<Double>> map, int necessary) {
            double valorMax = -1;
            String idEstacion = "";
            List<String> idsEstaciones = new ArrayList<>();

            for (int i = 0; i < necessary; i++) {
                for (Map.Entry<String, List<Double>> e : map.entrySet()) {
                    if (!idsEstaciones.contains(e.getKey()) && valorMax < e.getValue().get(0) / e.getValue().get(1)) {
                        valorMax = e.getValue().get(0) / e.getValue().get(1);
                        idEstacion = e.getKey();
                    }
                }
                idsEstaciones.add(idEstacion);
            }
            return idsEstaciones;
        }
    }

    public synchronized void stop() {
        monitorizacion.interrupt();
        synchronized (this) {
            notify();
        }
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Esperando mensaje. Pulsa return para terminar");
        Cliente cliente = new Cliente();
        Thread hiloEspera = new Thread(() -> {
            teclado.nextLine();
            cliente.stop();
        });
        hiloEspera.start();
        cliente.enviarMensaje();
        teclado.close();
    }
}

