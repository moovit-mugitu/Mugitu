package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.estacion.EstacionDao;
import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.dao.utilizacion.UtilizacionDao;
import Mooving.MUgituApi.entities.Estacionar;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/estacionar")
public class EstacionarApi {

    final UsuarioDao usuarioDao;
    final TipoUsuarioDao tipoUsuarioDao;
    final EstacionarDao estacionarDao;
    final UtilizacionDao utilizacionDao;
    final BiciDao biciDao;
    final EstacionDao estacionDao;

    public EstacionarApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao, EstacionarDao estacionarDao, UtilizacionDao utilizacionDao, BiciDao biciDao, EstacionDao estacionDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.estacionarDao = estacionarDao;
        this.utilizacionDao = utilizacionDao;
        this.biciDao = biciDao;
        this.estacionDao = estacionDao;
    }

    ///  GET METHODS  ///

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Estacionar> getEstacionarById(@PathVariable("id") long id) {
        Estacionar estacionar = estacionarDao.getEstacionar(id);
        if (estacionar == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacionar);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Estacionar>> getAllEstacionars() {
        List<Estacionar> estacionars = estacionarDao.getAllEstacionars();
        if (estacionars == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacionars);
    }

    @GetMapping(path = "/estacion/{id}")
    public ResponseEntity<List<Estacionar>> getEstacionarByEstacion(@PathVariable("id") long id) {
        List<Estacionar> estacionars = estacionarDao.getEstacionarByEstacion(id);
        if (estacionars == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacionars);
    }

    @GetMapping(path = "/bici/{id}")
    public ResponseEntity<List<Estacionar>> getEstacionarByBici(@PathVariable("id") long id) {
        List<Estacionar> estacionars = estacionarDao.getEstacionarByEstacion(id);
        if (estacionars == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacionars);
    }

    ///  PUT METHODS  ///

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Estacionar> editEstacionar(@PathVariable("id") long id, @RequestBody Estacionar estacionar) {
        if (estacionar == null || id != estacionar.getEstacionarId()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacionar);
    }

    @PutMapping(path = "/create/{biciId}/{estacionId}/{userId}")
    public ResponseEntity<Estacionar> createBici(
            @PathVariable("biciId") long biciId, @PathVariable("estacionId") long estacionId,
            @PathVariable("userId") long userId, HttpServletResponse response, Authentication authentication) {

        if(UserApi.getPrincipal(usuarioDao, authentication).getUserId() != userId &&
                !UserApi.getPrincipal(usuarioDao, authentication).getTipo_usuario().getDescripcion().equals("ADMIN")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        try {
            utilizacionDao.finishUtilizacion(biciId, userId);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Estacionar e = new Estacionar();
        e.setBici(biciDao.getBici(biciId));
        e.setEstacion(estacionDao.getEstacion(estacionId));
        e.setFechaInicio(new Date());
        Estacionar saved = estacionarDao.addEstacionar(e);
        return ResponseEntity.created(URI.create("/estacionar/id/" + saved.getEstacionarId())).build();
    }

    ///  DELETE METHODS  ///

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> createBici(@PathVariable("id") long id) {
        try {
            estacionarDao.deleteEstacionar(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }
}
