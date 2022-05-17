package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.estacion.EstacionDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.Estacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Estacion> editEstacion(@PathVariable("id") long id, @RequestBody Estacion estacion) {
        if (estacion == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacion);
    }
}
