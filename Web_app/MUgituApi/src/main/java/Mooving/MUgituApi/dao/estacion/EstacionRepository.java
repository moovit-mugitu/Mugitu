package Mooving.MUgituApi.dao.estacion;

import Mooving.MUgituApi.entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EstacionRepository extends JpaRepository<Estacion, Long> {
    List<Estacion> getEstacionsByActiva(Boolean activa);
    List<Estacion> getEstacionsByIa(Boolean ia);
}
