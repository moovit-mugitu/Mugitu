package Mooving.MUgituApi.dao.notificacionAveria;

import Mooving.MUgituApi.entities.NotificacionAveria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<NotificacionAveria, Long> {
    List<NotificacionAveria> getAllByNueva(Boolean nueva);
    List<NotificacionAveria> getAllByResuelta(Boolean resuelta);
    List<NotificacionAveria> getAllByUserUserId(Long userId);
}
