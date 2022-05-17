package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.averia.AveriaDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.Averia;
import Mooving.MUgituApi.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Averia> editAveria(@PathVariable("id") long id, @RequestBody Averia averia) {
        if (averia == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(averia);
    }
}
