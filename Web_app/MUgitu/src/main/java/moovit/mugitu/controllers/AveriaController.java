package moovit.mugitu.controllers;

import moovit.mugitu.entities.Averia;
import moovit.mugitu.entities.Bici;
import moovit.mugitu.entities.TipoAveria;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@Controller
@RequestMapping("/averia")
public class AveriaController {

    @GetMapping
    public String getAveriaMenu(Model model) {
        model.addAttribute("navPage", "averias");
        return "averiaMenu";
    }

    @GetMapping("/edit/{id}")
    public String editAveria(@PathVariable("id") long id, Model model) {
        ResponseEntity<Averia> averia = RestRequests.RestRequestWithHeaders("/averia/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);
        if(Objects.requireNonNull(averia.getBody()).getAveriaId() == id){
            model.addAttribute("averia", averia.getBody());
            model.addAttribute("url", "/edit/"+id);
            return "editAveria";
        }
        return "error";
    }

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public Averia editAveria(@PathVariable("id") long id, @ModelAttribute Averia averia, WebRequest request){
        int tipoAveriaId = Integer.parseInt(Objects.requireNonNull(request.getParameter("tipoAveriaId")));
        int biciId = Integer.parseInt(Objects.requireNonNull(request.getParameter("biciId")));

        ResponseEntity<TipoAveria> response1 = RestRequests.RestRequestWithHeaders(
                "/tipoAveria/id/"+tipoAveriaId, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), TipoAveria.class);
        ResponseEntity<Bici> response2 = RestRequests.RestRequestWithHeaders(
                "/bici/id/"+biciId, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici.class);

        averia.setAveriaId(id);
        averia.setTipoAveria(response1.getBody());
        averia.setBici(response2.getBody());
        ResponseEntity<Averia> response3 = RestRequests.RestRequestWithHeaders(
                "/averia/edit/"+id, HttpMethod.PUT, averia, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);
        return response3.getBody();
    }

    @GetMapping(path = "/all")
    public String getAllAverias(Model model) {
        ResponseEntity<Averia[]> response = RestRequests.RestRequestWithHeaders(
                "/averia/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia[].class);

        List<Averia> averias = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("averias", averias);
        return "averiaMenu";
    }

    @GetMapping(path = "/activas")
    public String getAveriasActivas(Model model) {
        ResponseEntity<Averia[]> response = RestRequests.RestRequestWithHeaders(
                "/averia/activas", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia[].class);

        List<Averia> averias = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("averias", averias);
        return "averiaMenu";
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Averia getAveriasById(@PathVariable("id") long id) {
        ResponseEntity<Averia> response = RestRequests.RestRequestWithHeaders(
                "/averia/id/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);

        return response.getBody();
    }

    @GetMapping(path = "/tipo")
    public String getAveriasByTipo(@RequestParam("tipoAveriaId") int tipo, Model model) {
        ResponseEntity<Averia[]> response = RestRequests.RestRequestWithHeaders(
                "/averia/tipo/"+tipo, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia[].class);

        List<Averia> averias = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("averias", averias);
        return "averiaMenu";
    }

    @GetMapping(path = "/create")
    public String createAveria(Model model) {
        model.addAttribute("averia", new Averia());
        model.addAttribute("url", "/create");
        return "editAveria";
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public Averia createAveria(WebRequest request, @ModelAttribute Averia averia) {
        int tipoAveriaId = Integer.parseInt(Objects.requireNonNull(request.getParameter("tipoAveriaId")));
        int biciId = Integer.parseInt(Objects.requireNonNull(request.getParameter("biciId")));

        ResponseEntity<TipoAveria> response1 = RestRequests.RestRequestWithHeaders(
                "/tipoAveria/id/"+tipoAveriaId, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), TipoAveria.class);
        ResponseEntity<Bici> response2 = RestRequests.RestRequestWithHeaders(
                "/bici/id/"+biciId, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Bici.class);

        averia.setTipoAveria(response1.getBody());
        averia.setBici(response2.getBody());

        ResponseEntity<Averia> response = RestRequests.RestRequestWithHeaders(
                "/averia/create", HttpMethod.PUT, averia, RestRequests.getToken(RestRequests.ACCESSTOKEN), Averia.class);
        return response.getBody();
    }

    @PostMapping(path = "/delete/{id}")
    @ResponseBody
    public String deleteAveria(@PathVariable("id") long id) {
        ResponseEntity<Void> response = RestRequests.RestRequestWithHeaders(
                "/averia/delete/"+id, HttpMethod.DELETE, RestRequests.getToken(RestRequests.ACCESSTOKEN), Void.class);

        return "deleted";
    }
}
