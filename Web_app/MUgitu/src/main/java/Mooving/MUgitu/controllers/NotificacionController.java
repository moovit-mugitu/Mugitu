package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.NotificacionAveria;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/notificacion")
public class NotificacionController {

    @GetMapping
    public String getNotificacionMenu(Model model) {
        model.addAttribute("notificacion", new NotificacionAveria());
        model.addAttribute("navPage", "notifications");
        return "notificacionMenu";
    }

    @GetMapping("/user/all")
    public String getNotificacionesUser(Model model) {
        ResponseEntity<NotificacionAveria[]> response = RestRequests.RestRequestWithHeaders("/notificacion/user/all",
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria[].class);

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(response.getBody()));
        model.addAttribute("notificacion", new NotificacionAveria());
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }

    @GetMapping("/user/all")
    public String getNotificacionesWorker(Model model) {
        ResponseEntity<NotificacionAveria[]> response = RestRequests.RestRequestWithHeaders("/notificacion/worker/all",
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria[].class);

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(response.getBody()));
        model.addAttribute("notificacion", new NotificacionAveria());
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }

    @PostMapping("/create")
    @ResponseBody
    public String createNotificacion(@ModelAttribute NotificacionAveria notificacion) {
        ResponseEntity<NotificacionAveria> response = RestRequests.RestRequestWithHeaders("/notificacion/create",
                HttpMethod.PUT, notificacion,RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria.class);

        return "updated";
    }
}
