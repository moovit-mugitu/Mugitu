package Mooving.MUgitu.dao.notificacionAveria;

import Mooving.MUgitu.entities.NotificacionAveria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<NotificacionAveria, Long> {
}
