package Mooving.MUgituApi.dao.averia;

import Mooving.MUgituApi.entities.Averia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AveriaRepository extends JpaRepository<Averia, Long> {
    List<Averia> getAveriasByTipoAveria(Integer tipoAveria);
}
