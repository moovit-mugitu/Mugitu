package Mooving.MUgituApi.dao.user;

import Mooving.MUgituApi.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDataAccessService implements UsuarioDao {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Usuario getUser(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editUser(Usuario user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteUser(Usuario user) {
        repository.delete(user);
    }

    @Override
    public Usuario addUser(Usuario user) {
        return repository.save(user);
    }

    @Override
    public Usuario getUserByDNI(String DNI) {
        return repository.getUsuarioByDNI(DNI);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return repository.getUsuarioByCorreo(email);
    }
}
