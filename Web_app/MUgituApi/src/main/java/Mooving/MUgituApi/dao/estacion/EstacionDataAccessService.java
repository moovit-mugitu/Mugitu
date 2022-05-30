package Mooving.MUgituApi.dao.estacion;


import Mooving.MUgituApi.entities.Estacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionDataAccessService implements EstacionDao {

    @Autowired
    private EstacionRepository repository;

    @Override
    public List<Estacion> getAllEstacions() {
        return repository.findAll();
    }

    @Override
    public Estacion getEstacion(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editEstacion(Estacion estacion) {
        repository.save(estacion);
    }

    @Override
    public void deleteEstacion(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteEstacion(Estacion estacion) {
        repository.delete(estacion);
    }

    @Override
    public Estacion addEstacion(Estacion estacion) {
        return repository.save(estacion);
    }

    @Override
    public List<Estacion> getEstacionesActivas(boolean activa) {
        return repository.getEstacionsByActiva(activa);
    }

    @Override
    public List<Estacion> getEstacionesConIa(boolean ia) {
        return repository.getEstacionsByIa(ia);
    }

}
