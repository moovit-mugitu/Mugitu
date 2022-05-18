package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.dao.evento.EventoDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.dao.utilizacion.UtilizacionDao;
import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Estacionar;
import Mooving.MUgituApi.entities.Evento;
import Mooving.MUgituApi.entities.Utilizacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bici")
public class BiciApi {

    final UsuarioDao usuarioDao;
    final TipoUsuarioDao tipoUsuarioDao;
    final BiciDao biciDao;
    final EstacionarDao estacionarDao;
    final EventoDao eventoDao;
    final UtilizacionDao utilizacionDao;

    public BiciApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao, BiciDao biciDao, EstacionarDao estacionarDao, EventoDao eventoDao, UtilizacionDao utilizacionDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.biciDao = biciDao;
        this.estacionarDao = estacionarDao;
        this.eventoDao = eventoDao;
        this.utilizacionDao = utilizacionDao;
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
        /*if (bici == null || bici.getBiciId() != id) return ResponseEntity.notFound().build();
        biciDao.editBici(bici);
        return ResponseEntity.ok(bici);*/
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/libre")
    public ResponseEntity<List<Bici>> getBiciLibres() {
        List<Estacionar> estacionarList = estacionarDao.getEstacionarSinFechaFin();
        List<Bici> bicis = estacionarList.stream().map(Estacionar::getBici).collect(Collectors.toList());
        return ResponseEntity.ok(bicis);
    }

    @GetMapping(path = "/parada")
    public ResponseEntity<List<Bici>> getBiciParadas() {
        List<Evento> eventos = eventoDao.getUltimosEventosByEstado(Bici.Estados.Pausa);
        List<Bici> bicis = eventos.stream().map(Evento::getBici).collect(Collectors.toList());
        return ResponseEntity.ok(bicis);
    }

    @GetMapping(path = "/ocupada")
    public ResponseEntity<List<Bici>> getBiciOcupadas() {
        List<Utilizacion> utilizaciones = utilizacionDao.getUtilizacionSinFin();
        List<Bici> bicis = utilizaciones.stream().map(Utilizacion::getBici).collect(Collectors.toList());
        return ResponseEntity.ok(bicis);
    }

}
