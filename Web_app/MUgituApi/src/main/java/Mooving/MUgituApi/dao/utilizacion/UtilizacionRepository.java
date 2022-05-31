package Mooving.MUgituApi.dao.utilizacion;

import Mooving.MUgituApi.entities.Utilizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilizacionRepository extends JpaRepository<Utilizacion, Long> {
    List<Utilizacion> getUtilizacionsByFechaFinIsNull();
    Utilizacion getUtilizacionByBiciBiciIdAndFechaFinIsNull(long id);
    List<Utilizacion> getAllByUserUserId(Long user_userId);
}
