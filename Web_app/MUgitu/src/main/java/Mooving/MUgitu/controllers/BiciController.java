package Mooving.MUgitu.controllers;

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
@RequestMapping("/bici")
public class BiciController {

    @GetMapping("/edit/{id}")
    public String editBici(@PathVariable("id") long id, Model model) {
        ResponseEntity<Bici> response = RestRequests.RestRequestWithHeaders("/bici/id/" + id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici.class);
        Bici bici = response.getBody();
        bici.setBiciId(id);
        model.addAttribute("bici", bici);
        return "editBici";
    }

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public String editBici(@PathVariable("id") long id, @ModelAttribute Bici bici, WebRequest request) {
        boolean electric = Objects.equals(request.getParameter("electric"), "on");
        bici.setElectrica(electric);
        bici.setBiciId(id);
        ResponseEntity<String> response = RestRequests.RestRequestWithHeaders(
                "/bici/edit/" + id, HttpMethod.PUT, bici, RestRequests.getToken(RestRequests.ACCESSTOKEN), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "updated";
        } else {
            return "Something went wrong";
        }
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Bici> getAllBicis() {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bici/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Bici getBiciById(@PathVariable("id") long id) {
        ResponseEntity<Bici> response = RestRequests.RestRequestWithHeaders(
                "/bici/id/" + id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici.class);

        return response.getBody();
    }

    @GetMapping(path = "/model/{model}")
    @ResponseBody
    public List<Bici> getBicisByModel(@PathVariable("model") String model) {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bici/model/" + model, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/electrica/{electric}")
    @ResponseBody
    public List<Bici> getBicisByElectrica(@PathVariable("electric") boolean electric) {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bici/electrica/" + electric, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/libre")
    @ResponseBody
    public List<Bici> getBicisLibres() {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bici/libre", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/parada")
    @ResponseBody
    public List<Bici> getBicisParada() {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bici/parada", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/ocupada")
    @ResponseBody
    public List<Bici> getBicisOcupada() {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bici/ocupada", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }
}