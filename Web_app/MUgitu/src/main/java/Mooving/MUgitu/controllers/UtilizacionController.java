package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Utilizacion;
import org.springframework.http.HttpMethod;
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
    public String getUtilizacionesPropias(Model model){
        ResponseEntity<Utilizacion[]> response = RestRequests.RestRequestWithHeaders("/utilizar/user",
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Utilizacion[].class);

        List<Utilizacion> utilizacions = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("utilizaciones", utilizacions);
        return "utilizarView";
    }

    @GetMapping(path = "/all")
    public String getAllUtilizaciones(Model model){
        ResponseEntity<Utilizacion[]> response = RestRequests.RestRequestWithHeaders("/utilizar/all",
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Utilizacion[].class);

        List<Utilizacion> utilizacions = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("utilizaciones", utilizacions);
        return "utilizarView";
    }

    @GetMapping(path = "/user/id/{id}")
    public String getUtilizacionesUserId(@PathVariable("id") long id, Model model){
        ResponseEntity<Utilizacion[]> response = RestRequests.RestRequestWithHeaders("/utilizar/user/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Utilizacion[].class);

        List<Utilizacion> utilizacions = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("utilizaciones", utilizacions);
        return "utilizarView";
    }
}
