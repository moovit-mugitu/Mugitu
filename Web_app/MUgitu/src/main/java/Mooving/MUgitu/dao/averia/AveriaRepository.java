package Mooving.MUgitu.dao.averia;

import Mooving.MUgitu.entities.Averia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AveriaRepository extends JpaRepository<Averia, Long> {
    List<Averia> getAveriasByTipoAveria(Integer tipoAveria);
}
