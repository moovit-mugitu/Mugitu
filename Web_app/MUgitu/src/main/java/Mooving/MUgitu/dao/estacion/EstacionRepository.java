package Mooving.MUgitu.dao.estacion;

import Mooving.MUgitu.entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EstacionRepository extends JpaRepository<Estacion, Long> {
    List<Estacion> getEstacionsByActiva(Boolean activa);
}
