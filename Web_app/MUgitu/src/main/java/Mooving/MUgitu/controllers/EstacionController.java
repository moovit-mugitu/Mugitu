package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Averia;
import Mooving.MUgitu.entities.Estacion;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/estacion")
public class EstacionController {

    @GetMapping("/edit/{id}")
    public String editEstacion(@PathVariable("id") long id, Model model) {
        ResponseEntity<Estacion> estacion = RestRequests.RestRequestWithHeaders("/estacion/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion.class);
        if(estacion.getBody() != null && estacion.getBody().getId() == id){
            model.addAttribute("estacion", estacion);
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
                "/estacion/edit/"+id, HttpMethod.PUT, estacion, RestRequests.getToken(RestRequests.ACCESSTOKEN), String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return "updated";
        }
        else{
            return "Something went wrong";
        }
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Estacion> getAllEstaciones() {
        ResponseEntity<Estacion[]> response = RestRequests.RestRequestWithHeaders(
                "/estacion/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Estacion getEstacionesById(@PathVariable("id") long id) {
        ResponseEntity<Estacion> response = RestRequests.RestRequestWithHeaders(
                "/estacion/id/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion.class);

        return response.getBody();
    }

    @GetMapping(path = "/activa/{activa}")
    @ResponseBody
    public List<Estacion> getEstacionesByActiva(@PathVariable("activa") boolean activa) {
        ResponseEntity<Estacion[]> response = RestRequests.RestRequestWithHeaders(
                "/estacion/activa/"+activa, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacion[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }
}
