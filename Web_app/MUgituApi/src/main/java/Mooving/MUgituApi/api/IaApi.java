package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.estacion.EstacionDao;
import Mooving.MUgituApi.entities.Estacion;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(path = "/ia")
public class IaApi {
    private static Date FECHA_IA;

    @Autowired
    EstacionDao estacionDao;

    public IaApi() throws ParseException {
        if (FECHA_IA == null) {
            SimpleDateFormat formatter = getDateFormat();
            String dateInString = "2021-07-01 05:00:00";
            FECHA_IA = formatter.parse(dateInString);
        }
    }

    @GetMapping(path = "/biciEstacion")
    public Map<Long, Integer[]> getBicisPorEstaciones() {
        Map<Long, Integer[]> map = new HashMap<>();
        List<Estacion> estaciones = estacionDao.getAllEstacions();

        for (Estacion estacion : estaciones) {
            if (estacion.getIa()) {
                int libres = 0;
                try {
                    libres = requestIa(estacion.getId()).intValue();
                    map.put(estacion.getId(), new Integer[]{libres, estacion.getPlazas()});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /*map.put(1L, new Integer[] { 2, 10 });
        map.put(2L, new Integer[] { 10, 15 });
        map.put(3L, new Integer[] { 7, 30 });
        map.put(4L, new Integer[] { 4, 20 });*/

        return map;
    }

    private Double requestIa(long id) throws URISyntaxException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        URI url = new URI("http://ia.mugitu.eus/");
        JSONObject jsonObject = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();

        headers.setContentType(MediaType.APPLICATION_JSON);
        jsonObject.put("data", getDateFormat().format(FECHA_IA));
        jsonObject.put("id", String.valueOf(id));

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);
        try {
            ResponseEntity<Double> responseEntity = restTemplate.postForEntity(url, requestEntity, Double.class);
            System.out.println(responseEntity.getBody());
            return (responseEntity.getBody() == null) ? (double) -1 : responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (double) -1;
    }

    private SimpleDateFormat getDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
    }
}
