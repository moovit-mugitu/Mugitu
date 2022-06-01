package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Averia;
import Mooving.MUgitu.entities.Bici;
import Mooving.MUgitu.entities.TipoAveria;
import Mooving.MUgitu.security.MyUserDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/averia")
public class AveriaController {

    @GetMapping
    public String getAveriaMenu(Model model, Authentication authentication) {
        MyUserDetails u = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("user", u.getUser());
        model.addAttribute("navPage", "averias");
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editAveria(@PathVariable("id") long id, Model model) {
        ResponseEntity<Averia> averia = RestRequests.RestRequestWithHeaders("/averia/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);
        if(averia.getBody() != null && averia.getBody().getAveriaId() == id){
            model.addAttribute("averia", averia.getBody());
            model.addAttribute("url", "/edit/"+id);
            return "editAveria";
        }
        return "error";
    }

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public Averia editAveria(@PathVariable("id") long id, @ModelAttribute Averia averia, WebRequest request) throws ParseException {
        int tipoAveriaId = Integer.parseInt(request.getParameter("tipoAveriaId"));
        int biciId = Integer.parseInt(request.getParameter("biciId"));
        String fechaFin = request.getParameter("fechaFin");

        ResponseEntity<TipoAveria> response1 = RestRequests.RestRequestWithHeaders(
                "/tipoAveria/id/"+tipoAveriaId, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), TipoAveria.class);
        ResponseEntity<Bici> response2 = RestRequests.RestRequestWithHeaders(
                "/bici/id/"+biciId, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici.class);

        averia.setAveriaId(id);
        averia.setTipoAveria(response1.getBody());
        averia.setBici(response2.getBody());
        if(fechaFin != null && !fechaFin.equals("")) averia.setFechaFin(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"). parse(fechaFin));
        ResponseEntity<Averia> response3 = RestRequests.RestRequestWithHeaders(
                "/averia/edit/"+id, HttpMethod.PUT, averia, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);
        return response3.getBody();
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

    @GetMapping(path = "/create")
    public String createAveria(Model model) {
        model.addAttribute("averia", new Averia());
        model.addAttribute("url", "/create");
        return "editAveria";
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public Averia createAveria(Model model, @ModelAttribute Averia averia) {
        ResponseEntity<Averia> response = RestRequests.RestRequestWithHeaders(
                "/averia/create", HttpMethod.PUT, averia, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);
        return response.getBody();
    }
}
