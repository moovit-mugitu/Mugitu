package Mooving.MUgituApi.dao.notificacionAveria;

import Mooving.MUgituApi.entities.NotificacionAveria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<NotificacionAveria, Long> {
}
