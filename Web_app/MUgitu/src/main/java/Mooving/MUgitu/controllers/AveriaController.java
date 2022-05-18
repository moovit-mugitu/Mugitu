package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Averia;
import Mooving.MUgitu.entities.Bici;
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
@RequestMapping("/averia")
public class AveriaController {

    @GetMapping("/edit/{id}")
    public String editAveria(@PathVariable("id") long id, Model model) {
        ResponseEntity<Averia> averia = RestRequests.RestRequestWithHeaders("/averia/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);
        if(averia.getBody() != null && averia.getBody().getAveriaId() == id){
            model.addAttribute("averia", averia);
            return "editAveria";
        }
        return "error";
    }

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public String editAveria(@PathVariable("id") long id, @ModelAttribute Averia averia, WebRequest request) {
        averia.setAveriaId(id);
        ResponseEntity<String> response = RestRequests.RestRequestWithHeaders(
                "/averia/edit/"+id, HttpMethod.PUT, averia, RestRequests.getToken(RestRequests.ACCESSTOKEN), String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return "updated";
        }
        else{
            return "Something went wrong";
        }
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Averia> getAllAverias() {
        ResponseEntity<Averia[]> response = RestRequests.RestRequestWithHeaders(
                "/averia/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Averia getAveriasById(@PathVariable("id") long id) {
        ResponseEntity<Averia> response = RestRequests.RestRequestWithHeaders(
                "/averia/id/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);

        return response.getBody();
    }

    @GetMapping(path = "/tipo/{tipo}")
    @ResponseBody
    public List<Averia> getAveriasByTipo(@PathVariable("tipo") int tipo) {
        ResponseEntity<Averia[]> response = RestRequests.RestRequestWithHeaders(
                "/averia/tipo/"+tipo, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }
}
