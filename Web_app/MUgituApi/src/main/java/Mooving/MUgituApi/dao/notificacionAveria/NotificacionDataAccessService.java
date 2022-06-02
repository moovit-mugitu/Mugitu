package Mooving.MUgituApi.dao.notificacionAveria;

import Mooving.MUgituApi.entities.NotificacionAveria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<NotificacionAveria> getNotificacionesNuevas(boolean nueva) {
        return repository.getAllByNueva(nueva);
    }

    @Override
    public List<NotificacionAveria> getNotificacionesResueltas(boolean resuelta) {
        return repository.getAllByResuelta(resuelta);
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

    @Override
    public List<NotificacionAveria> getNotificacionesByUser(Long userId) {
        return repository.getAllByUserUserId(userId);
    }

    @Override
    public void markAsNotNew(List<NotificacionAveria> notificaciones) {
        List<Integer> news = new ArrayList<>();

        for(NotificacionAveria n: notificaciones){
            if(n.getNueva()){
                news.add(notificaciones.indexOf(n));
                n.setNueva(false);
                repository.save(n);
            }
        }
        for(int i : news){
            notificaciones.get(i).setNueva(true);
        }
    }
}
