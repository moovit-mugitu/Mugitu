package Mooving.MUgituApi.dao.evento;

import Mooving.MUgituApi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
