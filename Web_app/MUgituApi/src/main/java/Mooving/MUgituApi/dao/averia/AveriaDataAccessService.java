package Mooving.MUgituApi.dao.averia;

import Mooving.MUgituApi.entities.Averia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AveriaDataAccessService implements AveriaDao {

    @Autowired
    private AveriaRepository repository;

    @Override
    public List<Averia> getAllAverias() {
        return repository.findAll();
    }

    @Override
    public Averia getAveria(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editAveria(Averia bici) {
        repository.save(bici);
    }

    @Override
    public void deleteAveria(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAveria(Averia bici) {
        repository.delete(bici);
    }

    @Override
    public void addAveria(Averia bici) {
        repository.save(bici);
    }

    @Override
    public List<Averia> getAveriaByTipo(Integer tipo) {
        return repository.getAveriasByTipoAveria(tipo);
    }
}
