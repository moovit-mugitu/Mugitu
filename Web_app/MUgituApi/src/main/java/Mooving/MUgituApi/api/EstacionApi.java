package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.estacion.EstacionDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.Averia;
import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Estacion;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estacion")
public class EstacionApi {

    final UsuarioDao usuarioDao;
    final TipoUsuarioDao tipoUsuarioDao;
    final EstacionDao estacionDao;

    public EstacionApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao, EstacionDao estacionDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.estacionDao = estacionDao;
    }

    ///  GET METHODS  ///

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Estacion> getEstacionById(@PathVariable("id") long id) {
        Estacion estacion = estacionDao.getEstacion(id);
        if (estacion == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacion);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Estacion>> getAllEstaciones() {
        List<Estacion> estaciones = estacionDao.getAllEstacions();
        if (estaciones == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estaciones);
    }

    @GetMapping(path = "/activa/{activa}")
    public ResponseEntity<List<Estacion>> getEstacionByActiva(@PathVariable("activa") boolean activa) {
        List<Estacion> estaciones = estacionDao.getEstacionesActivas(activa);
        if (estaciones == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estaciones);
    }

    /// PUT METHODS  ///

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Estacion> editEstacion(@PathVariable("id") long id, @RequestBody Estacion estacion) {
        if (estacion == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacion);
    }

    @PutMapping(path = "/create")
    public ResponseEntity<Estacion> createBici(@RequestBody Estacion estacion) {
        if (estacion == null || estacion.getId() != null) return ResponseEntity.notFound().build();
        Estacion saved = estacionDao.addEstacion(estacion);
        return ResponseEntity.created(URI.create("/estacion/id/" + saved.getId())).body(saved);
    }

    ///  DELETE METHODS  ///

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> createBici(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            estacionDao.deleteEstacion(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }
}
