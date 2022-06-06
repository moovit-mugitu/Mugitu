package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.estacion.EstacionDao;
import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.dao.evento.EventoDao;
import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.dao.utilizacion.UtilizacionDao;
import Mooving.MUgituApi.entities.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    final EstacionDao estacionDao;

    public BiciApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao, BiciDao biciDao, EstacionarDao estacionarDao, EventoDao eventoDao, UtilizacionDao utilizacionDao, EstacionDao estacionDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.biciDao = biciDao;
        this.estacionarDao = estacionarDao;
        this.eventoDao = eventoDao;
        this.utilizacionDao = utilizacionDao;
        this.estacionDao = estacionDao;
    }

    ///  GET METHODS  ///

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

    @GetMapping(path = "/libre")
    public ResponseEntity<List<Bici>> getBiciLibres() {
        List<Estacionar> estacionarList = estacionarDao.getEstacionarSinFechaFin();
        List<Bici> bicis = estacionarList.stream().map(Estacionar::getBici).collect(Collectors.toList());
        return ResponseEntity.ok(bicis);
    }

    @GetMapping(path = "/parada")
    public ResponseEntity<List<Bici>> getBiciParadas() {
        List<Evento> eventos = eventoDao.getUltimosEventosByEstado(false);
        List<Bici> bicis = eventos.stream().map(Evento::getBici).collect(Collectors.toList());

        return ResponseEntity.ok(bicis);
    }

    @GetMapping(path = "/ocupada")
    public ResponseEntity<List<Bici>> getBiciOcupadas() {
        List<Utilizacion> utilizaciones = utilizacionDao.getUtilizacionSinFin();
        List<Bici> bicis = utilizaciones.stream().map(Utilizacion::getBici).collect(Collectors.toList());
        return ResponseEntity.ok(bicis);
    }

    @GetMapping(path = "/random/estacion/{id}/{electrica}")
    public ResponseEntity<Long> getBiciEnEstacion(@PathVariable("id") long estacionId, @PathVariable("electrica") boolean electrica) {
        List<Estacionar> estacionars = estacionarDao.getEstacionarSinFechaFinByEstacion(estacionId);
        List<Bici> bicis = estacionars.stream().map(Estacionar::getBici)
                .filter(line -> line.getElectrica() == electrica)
                .collect(Collectors.toList());
        if (bicis.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(-1L);
        Random r = new Random();
        int pos = r.nextInt(bicis.size());
        return ResponseEntity.ok(bicis.get(pos).getBiciId());
    }

    @GetMapping(path = "/generar")
    public void generarBicisEstacionamientoUtilizacion() {
        /*for (int i = 0; i < 5000; i++) {
            Bici bici = new Bici();
            bici.setEstado(true);
            bici.setElectrica(new Random().nextBoolean());
            if (bici.getElectrica()) bici.setSOC(new Random().nextInt(21) + 80);
            bici.setModelo("BH " + i % 3);
            biciDao.addBici(bici);

            Estacionar estacionar = new Estacionar();
            estacionar.setFechaInicio(new Date());
            estacionar.setFechaFin((new Random().nextBoolean())?new Date(new Date().getTime()+1000*60*60):null);
            estacionar.setBici(bici);
            Estacion estacion;
            do{
                estacion = estacionDao.getEstacion(new Random().nextInt(271));
            }while (estacion == null);
            estacionar.setEstacion(estacion);
            estacionarDao.addEstacionar(estacionar);
            System.out.println(estacionar);
        }*/
        /*List<Estacionar> estacionars = estacionarDao.getEstacionarConFechaFin();
        for(Estacionar e : estacionars){
            Utilizacion u = new Utilizacion();
            u.setBici(e.getBici());
            u.setFechaInicio(e.getFechaFin());
            u.setUser(usuarioDao.getUser(new Random().nextInt(4)+4));
            utilizacionDao.addUtilizacion(u);
            System.out.println(u);
        }*/
    }

    ///  PUT METHODS  ///

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<Bici> editBici(@PathVariable("id") long id, @RequestBody Bici bici) {
        if (bici == null || bici.getBiciId() != id) return ResponseEntity.notFound().build();
        biciDao.editBici(bici);
        return ResponseEntity.ok(bici);
    }

    @PutMapping(path = "/create")
    public ResponseEntity<Bici> createBici(@RequestBody Bici bici) {
        if (bici == null || bici.getBiciId() != null) return ResponseEntity.notFound().build();
        Bici saved = biciDao.addBici(bici);

        return ResponseEntity.created(URI.create("/bici/id/" + saved.getBiciId())).body(saved);
    }

    ///  DELETE METHODS  ///

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> createBici(@PathVariable("id") long id) {
        try {
            biciDao.deleteBici(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

}
