package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.Estacionar;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estacionar")
public class EstacionarApi {

    final UsuarioDao usuarioDao;
    final TipoUsuarioDao tipoUsuarioDao;
    final EstacionarDao estacionarDao;

    public EstacionarApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao, EstacionarDao estacionarDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.estacionarDao = estacionarDao;
    }

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

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Estacionar> editEstacionar(@PathVariable("id") long id, @RequestBody Estacionar estacionar) {
        if (estacionar == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estacionar);
    }
}
