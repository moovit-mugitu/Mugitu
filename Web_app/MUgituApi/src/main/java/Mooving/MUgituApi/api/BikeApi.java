package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.Bici;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeApi {

    final UsuarioDao usuarioDao;
    final TipoUsuarioDao tipoUsuarioDao;
    final BiciDao biciDao;

    public BikeApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao, BiciDao biciDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.biciDao = biciDao;
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Bici> getBiciById(@PathVariable("id") long id) {
        Bici bici = biciDao.getBici(id);
        if (bici == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bici);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Bici>> getAllBicis() {
        List<Bici> bicis = biciDao.getAllBicis();
        if (bicis == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bicis);
    }

    @GetMapping(path = "/electrica/{electrica}")
    public ResponseEntity<List<Bici>> getBicisByElectrica(@PathVariable("electrica") boolean electrica) {
        List<Bici> bicis = biciDao.getBicisByElectrica(electrica);
        if (bicis == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bicis);
    }

    @GetMapping(path = "/model/{model}")
    public ResponseEntity<List<Bici>> getBicisByModel(@PathVariable("model") String model) {
        List<Bici> bicis = biciDao.getBicisByModel(model);
        if (bicis == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bicis);
    }

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Bici> editBici(@PathVariable("id") long id, @RequestBody Bici bici) {
        if (bici == null || bici.getBiciId() != id) return ResponseEntity.notFound().build();
        biciDao.editBici(bici);
        return ResponseEntity.ok(bici);
    }
}
