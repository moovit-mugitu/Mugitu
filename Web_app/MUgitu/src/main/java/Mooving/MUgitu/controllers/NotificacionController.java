package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Bici;
import Mooving.MUgitu.entities.NotificacionAveria;
import Mooving.MUgitu.entities.TipoAveria;
import Mooving.MUgitu.entities.Usuario;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/notificacion")
public class NotificacionController {

    @GetMapping
    public String getNotificacionMenu(Model model) {
        model.addAttribute("navPage", "notifications");
        return "notificacionMenu";
    }

    @GetMapping("/user/all")
    public String getNotificacionesUser(Model model) {
        ResponseEntity<NotificacionAveria[]> response = RestRequests.RestRequestWithHeaders("/notificacion/user/all",
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria[].class);

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(response.getBody()));
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }

    @GetMapping("/worker/all")
    public String getNotificacionesWorker(Model model) {
        ResponseEntity<NotificacionAveria[]> response = RestRequests.RestRequestWithHeaders("/notificacion/worker/all",
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria[].class);

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(response.getBody()));
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }

    @PostMapping("/worker/solved/{id}")
    @ResponseBody
    public String getNotificacionesWorker(@PathVariable("id") long id) {
        ResponseEntity<NotificacionAveria> response = RestRequests.RestRequestWithHeaders("/notificacion/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria.class);
        NotificacionAveria notificacionAveria = response.getBody();
        notificacionAveria.setResuelta(true);
        ResponseEntity<Void> response2 = RestRequests.RestRequestWithHeaders("/notificacion/edit",
                HttpMethod.PUT, notificacionAveria, RestRequests.getToken(RestRequests.ACCESSTOKEN),Void.class);

        return "updated";
    }

    @PostMapping("/create")
    @ResponseBody
    public String createNotificacion(WebRequest request) {
        String userId = request.getParameter("userId");
        Usuario user = RestRequests.RestRequestWithHeaders("/user/id/"+userId,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),Usuario.class).getBody();
        Bici bici = RestRequests.RestRequestWithHeaders("/bici/id/"+request.getParameter("bikeId"),
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),Bici.class).getBody();
        TipoAveria tipoAveria = RestRequests.RestRequestWithHeaders("/tipoAveria/id/"+request.getParameter("tipoAveriaId"),
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), TipoAveria.class).getBody();
        NotificacionAveria notificacion = new NotificacionAveria();
        notificacion.setUser(user);
        notificacion.setBici(bici);
        notificacion.setMensaje(request.getParameter("message"));
        notificacion.setTipoAveria(tipoAveria);

        //CASCAAAAAAAAAAAAA REVISAR
        ResponseEntity<NotificacionAveria> response = RestRequests.RestRequestWithHeaders("/notificacion/create",
                HttpMethod.PUT, notificacion,RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria.class);

        return "created";
    }
}
