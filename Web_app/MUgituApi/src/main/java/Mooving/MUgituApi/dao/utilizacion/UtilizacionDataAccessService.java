package Mooving.MUgituApi.dao.utilizacion;

import Mooving.MUgituApi.entities.Utilizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizacionDataAccessService implements UtilizacionDao {

    @Autowired
    private UtilizacionRepository repository;

    @Override
    public List<Utilizacion> getAllUtilizacions() {
        return repository.findAll();
    }

    @Override
    public Utilizacion getUtilizacion(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editUtilizacion(Utilizacion utilizacion) {
        repository.save(utilizacion);
    }

    @Override
    public void deleteUtilizacion(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteUtilizacion(Utilizacion utilizacion) {
        repository.delete(utilizacion);
    }

    @Override
    public void addUtilizacion(Utilizacion utilizacion) {
        repository.save(utilizacion);
    }

    @Override
    public List<Utilizacion> getUtilizacionSinFin() {
        return repository.getUtilizacionsByFechaFinIsNull();
    }
}
