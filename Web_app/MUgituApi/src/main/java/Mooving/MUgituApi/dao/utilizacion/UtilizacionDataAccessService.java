package Mooving.MUgituApi.dao.utilizacion;

import Mooving.MUgituApi.entities.Utilizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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
    public Utilizacion addUtilizacion(Utilizacion utilizacion) {
        return repository.save(utilizacion);
    }

    @Override
    public List<Utilizacion> getUtilizacionSinFin() {
        return repository.getUtilizacionsByFechaFinIsNull();
    }

    @Override
    public Utilizacion finishUtilizacion(Long biciId, long userId) {
        Utilizacion u = repository.getUtilizacionByBiciBiciIdAndFechaFinIsNull(biciId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(userId != u.getUser().getUserId() &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) throw new NoResultException();
        u.setFechaFin(new  java.util.Date());
        repository.save(u);
        return u;
    }
}
