package Mooving.MUgituApi.dao.estacionar;

import Mooving.MUgituApi.dao.bici.BiciRepository;
import Mooving.MUgituApi.dao.estacion.EstacionRepository;
import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Estacion;
import Mooving.MUgituApi.entities.Estacionar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionarDataAccessService implements EstacionarDao {

    @Autowired
    private EstacionarRepository repository;
    @Autowired
    private EstacionRepository repositoryEstacion;
    @Autowired
    private BiciRepository repositoryBici;

    @Override
    public List<Estacionar> getAllEstacionars() {
        return repository.findAll();
    }

    @Override
    public Estacionar getEstacionar(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editEstacionar(Estacionar estacionar) {
        repository.save(estacionar);
    }

    @Override
    public void deleteEstacionar(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteEstacionar(Estacionar estacionar) {
        repository.delete(estacionar);
    }

    @Override
    public Estacionar addEstacionar(Estacionar estacionar) {
        return repository.save(estacionar);
    }

    @Override
    public List<Estacionar> getEstacionarByEstacion(long id) {
        Estacion estacion = repositoryEstacion.getById(id);
        return repository.getEstacionarByEstacion(estacion);
    }

    @Override
    public List<Estacionar> getEstacionarByBici(long id) {
        Bici bici = repositoryBici.getById(id);
        return repository.getEstacionarByBici(bici);
    }

    @Override
    public List<Estacionar> getEstacionarSinFechaFin() {
        return repository.getEstacionarByFechaFinIsNull();
    }

    @Override
    public List<Estacionar> getEstacionarSinFechaFinByEstacion(long id) {
        Estacion estacion = repositoryEstacion.getById(id);
        return repository.getEstacionarByFechaFinIsNullAndEstacion(estacion);
    }
}
