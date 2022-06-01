package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.estacion.EstacionDao;
import Mooving.MUgituApi.entities.Estacion;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/ia")
public class IaApi {
    private static Date FECHA_INICIO_IA;

    final EstacionDao estacionDao;

    public IaApi(EstacionDao estacionDao) throws ParseException {
        if (FECHA_INICIO_IA == null) {
            SimpleDateFormat formatter = getDateFormat();
            String startDate = "2021-07-01 00:00:00";
            FECHA_INICIO_IA = formatter.parse(startDate);
        }
        this.estacionDao = estacionDao;
    }

    @GetMapping(path = "/biciEstacion")
    public ResponseEntity<Map<Long, Integer[]>> getBicisPorEstaciones() {
        Map<Long, Integer[]> map = new HashMap<>();
        List<Estacion> estaciones = estacionDao.getEstacionesConIa(true);

        for (Estacion estacion : estaciones) {
            int libres = 0;
            try {
                libres = requestIa(estacion.getId(),transformDateToPredict(new Date(new Date().getTime()+7_200_000))).intValue();
                map.put(estacion.getId(), new Integer[]{libres, estacion.getPlazas()});
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.ok(map);
    }

    @GetMapping(path ="/biciEstacion/{id}")
    public ResponseEntity<Integer> getBicisPorEstacion(@PathVariable("id") long id,
                                                       @RequestParam("date") String dateStr) {
        Date date = new Date();
        try{
            date = getDateFormat().parse(dateStr);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect date format");
        }

        Estacion estacion = estacionDao.getEstacion(id);
        if (!estacion.getIa()) {
            return ResponseEntity.badRequest().build();
        }

        int libres=0;
        try {
            libres = requestIa(id, transformDateToPredict(date)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(libres);
    }

    private Double requestIa(long id, Date date) throws URISyntaxException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        URI url = new URI("http://ia.mugitu.eus/");
        JSONObject jsonObject = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();

        headers.setContentType(MediaType.APPLICATION_JSON);
        jsonObject.put("data", getDateFormat().format(date));
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

    private SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    }

    private Date transformDateToPredict(Date date){
        /*
        LA IA PREDICE CON EXACTITUD 3 DIAS A PARTIR DEL TRAINING (JUEVES, VIERNES Y SABADO)
        DE LUNES-JUEVES PREDICE JUEVES, VIERNES PREDICE VIERNES, SABADO-DOMINGO PREDICE SABADO
         */
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int daysToSum = 0;
        switch (cal.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:
            case Calendar.SATURDAY:
                daysToSum = 2;
                break;
            case Calendar.FRIDAY:
                daysToSum = 1;
                break;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(FECHA_INICIO_IA);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, daysToSum);
        c.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));
        Date returnDate = c.getTime();
        return returnDate;
    }
}
