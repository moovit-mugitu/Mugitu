package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Bici;
import Mooving.MUgitu.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/bike")
public class BikeController {

    @GetMapping("/edit/{id}")
    public String editBici(@PathVariable("id") long id, Model model) {
        ResponseEntity<Bici> bici = RestRequests.RestRequestWithHeaders("/bike/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici.class);
        if(bici.getBody().getBiciId() == id){
            model.addAttribute("bici", bici);
            return "editBici";
        }
        return "error";
    }

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public String editBici(@PathVariable("id") long id, @ModelAttribute Bici bici, WebRequest request) {
        boolean electric = Objects.equals(request.getParameter("electric"), "on");
        bici.setElectrica(electric);
        bici.setBiciId(id);
        ResponseEntity<String> response = RestRequests.RestRequestWithHeaders(
                "/bike/edit/"+id, HttpMethod.PUT, bici, RestRequests.getToken(RestRequests.ACCESSTOKEN), String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return "updated";
        }
        else{
            return "Something went wrong";
        }
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Bici> getAllBicis() {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bike/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Bici getBiciById(@PathVariable("id") long id) {
        ResponseEntity<Bici> response = RestRequests.RestRequestWithHeaders(
                "/bike/id/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici.class);

        return response.getBody();
    }

    @GetMapping(path = "/model/{model}")
    @ResponseBody
    public List<Bici> getBicisByModel(@PathVariable("model") String model) {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bike/model/"+model, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/electrica/{electric}")
    @ResponseBody
    public List<Bici> getBicisByElectrica(@PathVariable("electric") boolean electric) {
        ResponseEntity<Bici[]> response = RestRequests.RestRequestWithHeaders(
                "/bike/electrica/"+electric, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }
}
