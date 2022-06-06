import com.rabbitmq.client.Channel;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

public class SimularMovimiento extends Thread {
    private final long biciId;
    private final long estacionIni;
    private final long userId;
    private final Channel channel;

    public SimularMovimiento(long biciId, long estacionIni, long userId, Channel channel) {
        this.biciId = biciId;
        this.estacionIni = estacionIni;
        this.userId = userId;
        this.channel = channel;
    }

    @Override
    public void run() {
        int estacionFin = new Random().nextInt(264) + 1;
        int movements = new Random().nextInt(6) + 10;  //De 10 a 15 notificaciones

        //Obtener Ubicacion de estacion Inicio/Fin y calcular incremento de latitud/longitud
        JSONObject estacionIniJson = getEstacion(estacionIni);
        JSONObject estacionFinJson = getEstacion(estacionFin);
        double latitud = 0;
        double longitud = 0;
        double latIncrement = 0;
        double lonIncrement = 0;
        try {
            latitud = Objects.requireNonNull(estacionIniJson).getDouble("latitud");
            longitud = estacionIniJson.getDouble("longitud");
            double laFin = Objects.requireNonNull(estacionFinJson).getDouble("latitud");
            double loFin = estacionFinJson.getDouble("longitud");
            latIncrement = (latitud - laFin) / movements;
            lonIncrement = (longitud - loFin) / movements;
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }

        //Simular movimiento de bici x veces
        try {
            for (int i = 0; i < movements; i++) {
                boolean estado = new Random().nextBoolean();
                boolean dollar = new Random().nextInt(10) == 1; // Un porcentaje de 1/10 para poner dolar
                channel.basicPublish(Cliente.EXCHANGE_NAME, Cliente.ROUTING_KEY_EVENTO, null, (
                        biciId + "/" + estado + "/" + latitud + ((dollar)?"$":"") + "/" + longitud).getBytes(StandardCharsets.UTF_8));
                latitud += latIncrement;
                longitud += lonIncrement;
                Thread.sleep(500);
            }
            channel.basicPublish(Cliente.EXCHANGE_NAME, Cliente.ROUTING_KEY_ESTACIONAR, null, (biciId+"/"+estacionFin+"/"+userId).getBytes(StandardCharsets.UTF_8));
            System.out.println("Estacionar bici "+biciId+"; Termina utilizacion");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getEstacion(long estacionId) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("http://localhost:8000/MUgitu/REST/api/estacion/id/" + estacionId))
                .header("accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return new JSONObject(response.body());
            } else {
                System.out.println("Request error: " + response.statusCode());
            }
        } catch (InterruptedException | JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
