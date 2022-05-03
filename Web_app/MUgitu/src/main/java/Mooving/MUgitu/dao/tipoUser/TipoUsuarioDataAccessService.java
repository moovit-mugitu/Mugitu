package Mooving.MUgitu.dao.tipoUser;

import Mooving.MUgitu.entities.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioDataAccessService implements TipoUsuarioDao {

    @Autowired
    private TipoUsuarioRepository repository;

    @Override
    public List<TipoUsuario> getAllTipoUsuarios() {
        return repository.findAll();
    }

    @Override
    public TipoUsuario getTipoUsuario(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editTipoUsuario(TipoUsuario tipoUsuario) {
        repository.save(tipoUsuario);
    }

    @Override
    public void deleteTipoUsuario(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteTipoUsuario(TipoUsuario tipoUsuario) {
        repository.delete(tipoUsuario);
    }

    @Override
    public void addTipoUsuario(TipoUsuario tipoUsuario) {
        repository.save(tipoUsuario);
    }
}
