package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.notificacionAveria.NotificacionDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.NotificacionAveria;
import Mooving.MUgituApi.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/notificacion")
public class NotificacionApi {

    @Autowired
    NotificacionDao notificacionDao;
    @Autowired
    UsuarioDao usuarioDao;

    @GetMapping(path = "/worker/all")
    public ResponseEntity<List<NotificacionAveria>> getAllNotificaciones() {
        List<NotificacionAveria> notificaciones = notificacionDao.getAllNotificacions();
        for(NotificacionAveria n : notificaciones){
            if(n.getNueva()){
                n.setNueva(false);
                notificacionDao.editNotificacion(n);
            }
        }
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping(path = "/user/all")
    public ResponseEntity<List<NotificacionAveria>> getAllNotificacionesByUser(Authentication authentication) {
        Usuario user = UserApi.getPrincipal(usuarioDao, authentication);
        List<NotificacionAveria> notificaciones = notificacionDao.getNotificacionesByUser(user.getUserId());

        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<NotificacionAveria> getNotificacionById(@PathVariable("id") long id,
                                                                  Authentication authentication) {
        NotificacionAveria notificacion = notificacionDao.getNotificacion(id);
        Usuario user = UserApi.getPrincipal(usuarioDao, authentication);
        if (!user.getTipo_usuario().getDescripcion().equals("WORKER") &&
                !user.getTipo_usuario().getDescripcion().equals("ADMIN") &&
                !Objects.equals(user.getUserId(), notificacion.getUser().getUserId()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(notificacion);
    }

    @GetMapping(path = "/worker/consultar/nuevas")
    public ResponseEntity<Boolean> consultarHayNotificacionesNuevas() {
        List<NotificacionAveria> notificaciones = notificacionDao.getNotificacionesNuevas(true);
        if (notificaciones.size() == 0) return ResponseEntity.ok(false);

        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "/worker/nuevas/{nueva}")
    public ResponseEntity<List<NotificacionAveria>> getNotificacionesNuevas(@PathVariable("nueva") boolean nueva) {
        List<NotificacionAveria> notificaciones = notificacionDao.getNotificacionesNuevas(nueva);
        //Quitar de nuevas una vez ya se miran cuales son
        for(NotificacionAveria n : notificaciones){
            n.setNueva(false);
            notificacionDao.editNotificacion(n);
        }
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping(path = "/worker/resuelta/{resuelta}")
    public ResponseEntity<List<NotificacionAveria>> getNotificacionesResueltas(@PathVariable("resuelta") boolean resuelta) {
        List<NotificacionAveria> notificaciones = notificacionDao.getNotificacionesResueltas(resuelta);
        //Quitar de nuevas una vez ya se miran cuales son
        for(NotificacionAveria n : notificaciones){
            if(n.getNueva()){
                n.setNueva(false);
                notificacionDao.editNotificacion(n);
            }
        }
        return ResponseEntity.ok(notificaciones);
    }

    @PutMapping(path = "/create")
    public ResponseEntity<NotificacionAveria> createNotificacion(@RequestBody NotificacionAveria notificacion) {
        NotificacionAveria saved = notificacionDao.addNotificacion(notificacion);

        return ResponseEntity.created(URI.create("/notificacion/id/" + saved.getNotificacionId())).body(saved);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<NotificacionAveria> editNotificacion(@RequestBody NotificacionAveria notificacion) {
        notificacionDao.editNotificacion(notificacion);

        return ResponseEntity.ok().build();
    }
}
