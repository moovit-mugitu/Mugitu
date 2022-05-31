package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Utilizacion;
import Mooving.MUgitu.security.MyUserDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/utilizar")
public class UtilizacionController {

    @PostMapping(path = "/create")
    @ResponseBody
    public Utilizacion createUtilizacion(WebRequest request) {
        long biciId = Long.parseLong(Objects.requireNonNull(request.getParameter("biciId")));
        long userId = Long.parseLong(Objects.requireNonNull(request.getParameter("userId")));

        ResponseEntity<Utilizacion> response = RestRequests.RestRequestWithHeaders("/utilizar/create/" + biciId + "/" + userId,
                HttpMethod.PUT, RestRequests.getToken(RestRequests.ACCESSTOKEN), Utilizacion.class);
        return response.getBody();
    }

    @GetMapping(path = "/user")
    @ResponseBody
    public List<Utilizacion> getUtilizacionesPropias(Authentication authentication){
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        ResponseEntity<Utilizacion[]> response = RestRequests.RestRequestWithHeaders("/utilizar/user/"+userDetails.getUser().getUserId(),
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Utilizacion[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Utilizacion> getAllUtilizaciones(Authentication authentication){
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        ResponseEntity<Utilizacion[]> response = RestRequests.RestRequestWithHeaders("/utilizar/user/"+userDetails.getUser().getUserId(),
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Utilizacion[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }

    @GetMapping(path = "/user/{id}")
    @ResponseBody
    public List<Utilizacion> getUtilizacionesUserId(@PathVariable("id") long id){
        ResponseEntity<Utilizacion[]> response = RestRequests.RestRequestWithHeaders("/utilizar/user/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Utilizacion[].class);

        return new ArrayList<>(Arrays.asList(response.getBody()));
    }
}
