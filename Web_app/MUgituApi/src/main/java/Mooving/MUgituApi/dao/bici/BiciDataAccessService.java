package Mooving.MUgituApi.dao.bici;

import Mooving.MUgituApi.entities.Bici;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BiciDataAccessService implements BiciDao {

    @Autowired
    private BiciRepository repository;

    @Override
    public List<Bici> getAllBicis() {
        return repository.findAll();
    }

    @Override
    public Bici getBici(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editBici(Bici bici) {
        repository.save(bici);
    }

    @Override
    public void deleteBici(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteBici(Bici bici) {
        repository.delete(bici);
    }

    @Override
    public void addBici(Bici bici) {
        repository.save(bici);
    }

    @Override
    public List<Bici> getBicisByElectrica(Boolean electrica) {
        return repository.getBicisByElectrica(electrica);
    }

    @Override
    public List<Bici> getBicisByModel(String model) {
        return repository.getBicisByModelo(model);
    }
}
