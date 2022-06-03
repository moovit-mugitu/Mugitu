package Mooving.MUgituApi.dao.averia;

import Mooving.MUgituApi.dao.tipoAveria.TipoAveriaRepository;
import Mooving.MUgituApi.entities.Averia;
import Mooving.MUgituApi.entities.TipoAveria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AveriaDataAccessService implements AveriaDao {

    @Autowired
    private AveriaRepository repository;
    @Autowired
    private TipoAveriaRepository repositoryTipo;

    @Override
    public List<Averia> getAllAverias() {
        return repository.findAll();
    }

    @Override
    public Averia getAveria(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editAveria(Averia averia) {
        repository.save(averia);
    }

    @Override
    public void deleteAveria(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAveria(Averia averia) {
        repository.delete(averia);
    }

    @Override
    public Averia addAveria(Averia averia) {
        return repository.save(averia);
    }

    @Override
    public List<Averia> getAveriaByTipo(Integer tipo) {
        TipoAveria tipoAveria = repositoryTipo.getById(tipo);
        return repository.getAveriasByTipoAveria(tipoAveria);
    }

    @Override
    public List<Averia> getAveriasActivas() {
        return repository.getAveriasByFechaFinIsNull();
    }
}
