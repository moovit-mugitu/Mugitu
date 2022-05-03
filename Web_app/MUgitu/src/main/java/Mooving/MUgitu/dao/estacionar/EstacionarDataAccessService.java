package Mooving.MUgitu.dao.estacionar;

import Mooving.MUgitu.entities.Estacionar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionarDataAccessService implements EstacionarDao {

    @Autowired
    private EstacionarRepository repository;

    @Override
    public List<Estacionar> getAllEstacionars() {
        return repository.findAll();
    }

    @Override
    public Estacionar getEstacionar(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editEstacionar(Estacionar bici) {
        repository.save(bici);
    }

    @Override
    public void deleteEstacionar(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteEstacionar(Estacionar bici) {
        repository.delete(bici);
    }

    @Override
    public void addEstacionar(Estacionar bici) {
        repository.save(bici);
    }
}
