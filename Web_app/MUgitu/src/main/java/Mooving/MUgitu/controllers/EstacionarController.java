package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Estacionar;
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

@Controller
@RequestMapping("/estacionar")
public class EstacionarController {

    @GetMapping("/edit/{id}")
    public String editEstacionar(@PathVariable("id") long id, Model model) {
        ResponseEntity<Estacionar> estacionar = RestRequests.RestRequestWithHeaders("/estacionar/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar.class);
        if(estacionar.getBody() != null && estacionar.getBody().getEstacionarId() == id){
            model.addAttribute("estacionar", estacionar);
            return "editEstacionar";
        }
        return "error";
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Estacionar> getAllEstacionars() {
        ResponseEntity<Estacionar[]> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Estacionar getEstacionarsById(@PathVariable("id") long id) {
        ResponseEntity<Estacionar> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/id/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar.class);

        return response.getBody();
    }

    @GetMapping(path = "/estacion/{id}")
    @ResponseBody
    public List<Estacionar> getEstacionarsByEstacion(@PathVariable("id") long id) {
        ResponseEntity<Estacionar[]> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/estacion/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/bici/{id}")
    @ResponseBody
    public List<Estacionar> getEstacionarsByBici(@PathVariable("id") long id) {
        ResponseEntity<Estacionar[]> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/bici/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    ///  POST  ///

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public String editEstacionar(@PathVariable("id") long id, @ModelAttribute Estacionar estacionar, WebRequest request) {
        estacionar.setEstacionarId(id);
        ResponseEntity<String> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/edit/"+id, HttpMethod.PUT, estacionar, RestRequests.getToken(RestRequests.ACCESSTOKEN), String.class);
        return "updated";
    }

    @PostMapping(path = "/delete/{id}")
    @ResponseBody
    public String deleteEstacionar(@PathVariable("id") long id) {
        ResponseEntity<Void> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/delete/" + id, HttpMethod.DELETE, RestRequests.getToken(RestRequests.ACCESSTOKEN), Void.class);
        return "deleted";
    }
}
