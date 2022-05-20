package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.tipoAveria.TipoAveriaDao;
import Mooving.MUgituApi.entities.Averia;
import Mooving.MUgituApi.entities.TipoAveria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipoAveria")
public class TipoAveriaApi {

    @Autowired
    TipoAveriaDao tipoAveriaDao;

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<TipoAveria> getTipoAveriaById(@PathVariable("id") int id) {
        TipoAveria averia = tipoAveriaDao.getTipoAveria(id);
        if (averia == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(averia);
    }
}
