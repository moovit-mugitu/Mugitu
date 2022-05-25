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
    final static String EXCHANGE_NAME = "avisosBicis";

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

    public Map<Long, Integer[]> conseguirbicis() throws IOException, InterruptedException, JSONException {
        Map<Long, Integer[]> map = new HashMap<>();
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
            Iterator<Long> keys = json.keys();
            while(keys.hasNext()) {
                Long key = keys.next();
                Integer[] values = (Integer[]) json.get(String.valueOf(key));
                map.put(key, values);
            }
            System.out.println(json);
        } else {
            System.out.println("Request error: "+response.statusCode());
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
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

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
            String linea;
            int bicis;
            int id;
            Map<Long, Integer[]> map = new HashMap<>();
            List<Long> estaciones = new ArrayList<>();
            Random generador = new Random();
            try {
                while (!this.isInterrupted()) {
                    map = cliente.conseguirbicis();
                    //HACER CALCULOS DE PORCENTAJE EN MAPA
                    //////////////////////////
                    //////////////////////////
                    //////////////////////////
                    estaciones = map.entrySet().stream()
                            .filter(e -> ((float)e.getValue()[0]/e.getValue()[1]) < 0.25)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());
                    linea = "eii";
                    if (!this.isInterrupted()) {
                        channel.basicPublish(EXCHANGE_NAME, "", null, linea.getBytes());
                        System.out.println(" Enviado: " + linea);
                        Thread.sleep(1000);
                    }
                }
            } catch (IOException | InterruptedException | JSONException e) {
                System.out.println("hilo interrumpido");
            }
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

