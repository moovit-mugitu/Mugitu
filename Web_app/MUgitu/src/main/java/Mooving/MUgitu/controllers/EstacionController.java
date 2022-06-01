package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Estacion;
import org.apache.tomcat.jni.Local;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/estacion")
public class EstacionController {

    @GetMapping
    public String getEstacionMenu(Model model){
        model.addAttribute("navPage", "stations");
        return "estacionMenu";
    }

    @GetMapping("/edit/{id}")
    public String editEstacion(@PathVariable("id") long id, Model model) {
        ResponseEntity<Estacion> estacion = RestRequests.RestRequestWithHeaders("/estacion/id/" + id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion.class);
        if (estacion.getBody() != null && estacion.getBody().getId() == id) {
            model.addAttribute("estacion", estacion.getBody());
            model.addAttribute("url", "/edit/" + id);
            return "editEstacion";
        }
        return "error";
    }

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public String editEstacion(@PathVariable("id") long id, @ModelAttribute Estacion estacion, WebRequest request) {
        boolean active = Objects.equals(request.getParameter("active"), "on");
        estacion.setActiva(active);
        estacion.setId(id);
        ResponseEntity<String> response = RestRequests.RestRequestWithHeaders(
                "/estacion/edit/" + id, HttpMethod.PUT, estacion, RestRequests.getToken(RestRequests.ACCESSTOKEN), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "updated";
        } else {
            return "Something went wrong";
        }
    }

    @GetMapping("/create")
    public String createEstacion(Model model) {
        model.addAttribute("url", "/create");
        model.addAttribute("estacion", new Estacion());
        return "editEstacion";
    }

    @PostMapping("/create")
    public String createEstacion(@ModelAttribute Estacion estacion) {
        ResponseEntity<Estacion> response = RestRequests.RestRequestWithHeaders("/create", HttpMethod.PUT, estacion,
                RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion.class);

        return "redirect:/estacion/id/"+response.getBody().getId();
    }

    @GetMapping(path = "/all")
    public String getAllEstaciones(Model model) {
        ResponseEntity<Estacion[]> response = RestRequests.RestRequestWithHeaders(
                "/estacion/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion[].class);

        List<Estacion> estaciones = new ArrayList<>(Arrays.asList(response.getBody()));
        model.addAttribute("stations", estaciones);

        return "estacionView";
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Estacion getEstacionesByIdPath(@PathVariable("id") long id) {
        ResponseEntity<Estacion> response = RestRequests.RestRequestWithHeaders(
                "/estacion/id/" + id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion.class);

        return response.getBody();
    }

    @GetMapping(path = "/id")
    @ResponseBody
    public Estacion getEstacionesByIdRequest(@RequestParam("id") long id) {
        ResponseEntity<Estacion> response = RestRequests.RestRequestWithHeaders(
                "/estacion/id/" + id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion.class);

        return response.getBody();
    }

    @GetMapping(path = "/activa/{activa}")
    public String getEstacionesByActiva(@PathVariable("activa") boolean activa, Model model) {
        ResponseEntity<Estacion[]> response = RestRequests.RestRequestWithHeaders(
                "/estacion/activa/" + activa, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion[].class);

        List<Estacion> estaciones = new ArrayList<>(Arrays.asList(response.getBody()));
        model.addAttribute("stations", estaciones);

        return "estacionView";
    }

    @GetMapping(path = "/prediccion")
    @ResponseBody
    public Integer predecirBicisEnEstacion(WebRequest request, @RequestParam("id") long estacionId) {
        Date date = new Date();
        try{
            String dateStr = request.getParameter("date");
            LocalDateTime l = LocalDateTime.parse(dateStr);
            date = java.sql.Timestamp.valueOf(l);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect date format");
        }
        ResponseEntity<Integer> response = RestRequests.RestRequestWithHeaders("/ia/biciEstacion/"+estacionId+"?date="+date.toString().substring(0, date.toString().length()-2),
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Integer.class);

        return Objects.requireNonNull(response.getBody());
    }
}
