package Mooving.MUgituApi.dao.tipoAveria;

import Mooving.MUgituApi.entities.TipoAveria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAveriaDataAccessService implements TipoAveriaDao {

    @Autowired
    private TipoAveriaRepository repository;

    @Override
    public List<TipoAveria> getAllTipoAverias() {
        return repository.findAll();
    }

    @Override
    public TipoAveria getTipoAveria(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editTipoAveria(TipoAveria tipoAveria) {
        repository.save(tipoAveria);
    }

    @Override
    public void deleteTipoAveria(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteTipoAveria(TipoAveria tipoAveria) {
        repository.delete(tipoAveria);
    }

    @Override
    public TipoAveria addTipoAveria(TipoAveria tipoAveria) {
        return repository.save(tipoAveria);
    }
}
