package moovit.mugitu.controllers;

import moovit.mugitu.entities.Bici;
import moovit.mugitu.entities.TipoAveria;
import moovit.mugitu.entities.Usuario;
import moovit.mugitu.entities.NotificacionAveria;
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

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }

    @GetMapping("/worker/all")
    public String getNotificacionesWorker(Model model) {
        ResponseEntity<NotificacionAveria[]> response = RestRequests.RestRequestWithHeaders("/notificacion/worker/all",
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria[].class);

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }

    @GetMapping(path = "/worker/nuevas/{nueva}")
    public String getNotificacionesNuevas(@PathVariable("nueva") boolean nueva, Model model) {
        ResponseEntity<NotificacionAveria[]> response = RestRequests.RestRequestWithHeaders("/notificacion/worker/nuevas/"+nueva,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), NotificacionAveria[].class);

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }

    @GetMapping(path = "/worker/resuelta/{resuelta}")
    public String getNotificacionesResueltas(@PathVariable("resuelta") boolean resuelta, Model model) {
        ResponseEntity<NotificacionAveria[]> response = RestRequests.RestRequestWithHeaders("/notificacion/worker/resuelta/"+resuelta,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), NotificacionAveria[].class);

        List<NotificacionAveria> notificaciones = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("notificaciones", notificaciones);
        return "notificacionMenu";
    }


    @PostMapping("/worker/solved/{id}")
    @ResponseBody
    public String getNotificacionesWorker(@PathVariable("id") long id) {
        ResponseEntity<NotificacionAveria> response = RestRequests.RestRequestWithHeaders("/notificacion/id/"+id,
                HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria.class);
        NotificacionAveria notificacionAveria = Objects.requireNonNull(response.getBody());
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

        ResponseEntity<NotificacionAveria> response = RestRequests.RestRequestWithHeaders("/notificacion/create",
                HttpMethod.PUT, notificacion,RestRequests.getToken(RestRequests.ACCESSTOKEN),NotificacionAveria.class);

        return "created";
    }


}
