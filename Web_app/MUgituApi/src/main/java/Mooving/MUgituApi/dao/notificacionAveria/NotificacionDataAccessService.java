package Mooving.MUgituApi.dao.notificacionAveria;

import Mooving.MUgituApi.entities.NotificacionAveria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionDataAccessService implements NotificacionDao {

    @Autowired
    private NotificacionRepository repository;

    @Override
    public List<NotificacionAveria> getAllNotificacions() {
        return repository.findAll();
    }

    @Override
    public NotificacionAveria getNotificacion(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editNotificacion(NotificacionAveria notificacion) {
        repository.save(notificacion);
    }

    @Override
    public void deleteNotificacion(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteNotificacion(NotificacionAveria notificacion) {
        repository.delete(notificacion);
    }

    @Override
    public NotificacionAveria addNotificacion(NotificacionAveria notificacion) {
        return repository.save(notificacion);
    }
}