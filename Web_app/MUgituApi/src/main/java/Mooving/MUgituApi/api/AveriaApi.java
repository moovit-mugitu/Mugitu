package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.averia.AveriaDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.Averia;
import Mooving.MUgituApi.entities.Usuario;
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
@RequestMapping("/averia")
public class AveriaApi {

    final UsuarioDao usuarioDao;
    final TipoUsuarioDao tipoUsuarioDao;
    final AveriaDao averiaDao;

    public AveriaApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao, AveriaDao averiaDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.averiaDao = averiaDao;
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Averia> getAveriaById(@PathVariable("id") long id) {
        Averia averia = averiaDao.getAveria(id);
        if (averia == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(averia);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Averia>> getAllAverias() {
        List<Averia> averias = averiaDao.getAllAverias();
        if (averias == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(averias);
    }

    @GetMapping(path = "/tipo/{tipo}")
    public ResponseEntity<List<Averia>> getAveriaByTipo(@PathVariable("tipo") int tipo) {
        List<Averia> averias = averiaDao.getAveriaByTipo(tipo);
        if (averias == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(averias);
    }

    ///  PUT METHODS  ///

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Averia> editAveria(@PathVariable("id") long id, @RequestBody Averia averia) {
        if (averia == null || averia.getAveriaId() != id) return ResponseEntity.notFound().build();
        averiaDao.editAveria(averia);
        return ResponseEntity.ok(averia);
    }

    @PutMapping(path = "/create")
    public ResponseEntity<Averia> createAveria(@RequestBody Averia averia) {
        if (averia == null || averia.getAveriaId() != null) return ResponseEntity.notFound().build();
        Averia saved = averiaDao.addAveria(averia);
        return ResponseEntity.created(URI.create("/averia/id/" + saved.getAveriaId())).build();
    }

    ///  DELETE METHODS  ///

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> createAveria(@PathVariable("id") long id) {
        try {
            averiaDao.deleteAveria(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }
}
