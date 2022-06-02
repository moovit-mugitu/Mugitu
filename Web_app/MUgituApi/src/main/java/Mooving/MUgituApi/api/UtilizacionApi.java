package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.dao.utilizacion.UtilizacionDao;
import Mooving.MUgituApi.entities.Usuario;
import Mooving.MUgituApi.entities.Utilizacion;
import Mooving.MUgituApi.security.MyUserDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/utilizar")
public class UtilizacionApi {

    final UtilizacionDao utilizacionDao;
    final BiciDao biciDao;
    final UsuarioDao usuarioDao;
    final EstacionarDao estacionarDao;

    public UtilizacionApi(UtilizacionDao utilizacionDao, BiciDao biciDao, UsuarioDao usuarioDao, EstacionarDao estacionarDao) {
        this.utilizacionDao = utilizacionDao;
        this.biciDao = biciDao;
        this.usuarioDao = usuarioDao;
        this.estacionarDao = estacionarDao;
    }

    @PutMapping(path = "/create/{biciId}/{userId}")
    public ResponseEntity<Utilizacion> createUtilizacion(@PathVariable("biciId") long biciId,
                                                         @PathVariable("userId") long userId,
                                                         Authentication authentication) {
        if (UserApi.getPrincipal(usuarioDao, authentication).getUserId() != userId &&
                !UserApi.getPrincipal(usuarioDao, authentication).getTipo_usuario().getDescripcion().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Utilizacion u = new Utilizacion();
        u.setBici(biciDao.getBici(biciId));
        u.setFechaInicio(new Date());
        u.setUser(usuarioDao.getUser(userId));
        try {
            estacionarDao.finishEstacionar(biciId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        Utilizacion saved = utilizacionDao.addUtilizacion(u);
        return ResponseEntity.created(URI.create("/utilizar/id/" + saved.getUtilizaId())).body(saved);
    }

    @GetMapping(path = "/user")
    public ResponseEntity<List<Utilizacion>> getUtilizacionesPropias(Authentication authentication) {
        Usuario user = UserApi.getPrincipal(usuarioDao, authentication);
        List<Utilizacion> u = utilizacionDao.getUtilizacionByUser(user.getUserId());

        return ResponseEntity.ok(u);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Utilizacion>> getAllUtilizaciones() {
        List<Utilizacion> u = utilizacionDao.getAllUtilizacions();

        return ResponseEntity.ok(u);
    }

    @GetMapping(path = "/user/id/{id}")
    public ResponseEntity<List<Utilizacion>> getUtilizacionesUserId(@PathVariable("id") long id) {
        List<Utilizacion> u = utilizacionDao.getUtilizacionByUser(id);

        return ResponseEntity.ok(u);
    }
}
