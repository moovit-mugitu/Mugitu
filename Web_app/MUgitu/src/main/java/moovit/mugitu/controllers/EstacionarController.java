package moovit.mugitu.controllers;

import moovit.mugitu.entities.Estacionar;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/estacionar")
public class EstacionarController {

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Estacionar> getAllEstacionars() {
        ResponseEntity<Estacionar[]> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar[].class);

        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
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

        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
    }

    @GetMapping(path = "/bici/{id}")
    @ResponseBody
    public List<Estacionar> getEstacionarsByBici(@PathVariable("id") long id) {
        ResponseEntity<Estacionar[]> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/bici/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar[].class);

        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
    }

    ///  POST  ///

    @PostMapping(path = "/delete/{id}")
    @ResponseBody
    public String deleteEstacionar(@PathVariable("id") long id) {
        ResponseEntity<Void> response = RestRequests.RestRequestWithHeaders(
                "/estacionar/delete/" + id, HttpMethod.DELETE, RestRequests.getToken(RestRequests.ACCESSTOKEN), Void.class);
        return "deleted";
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public String createEstacionar(WebRequest request) {
        long biciId = Long.parseLong(Objects.requireNonNull(request.getParameter("biciId")));
        long estacionId = Long.parseLong(Objects.requireNonNull(request.getParameter("estacionId")));
        long userId = Long.parseLong(Objects.requireNonNull(request.getParameter("userId")));

        ResponseEntity<Estacionar> response = RestRequests.RestRequestWithHeaders("/estacionar/create/" + biciId + "/" + estacionId + "/" + userId,
                HttpMethod.PUT, RestRequests.getToken(RestRequests.ACCESSTOKEN), Estacionar.class);

        return "created";
    }
}
