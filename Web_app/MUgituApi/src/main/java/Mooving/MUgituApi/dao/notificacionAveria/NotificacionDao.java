package Mooving.MUgituApi.dao.notificacionAveria;

import Mooving.MUgituApi.entities.NotificacionAveria;

import java.util.List;

public interface NotificacionDao {
     List<NotificacionAveria> getAllNotificacions();
     List<NotificacionAveria> getNotificacionesNuevas(boolean nueva);
     List<NotificacionAveria> getNotificacionesResueltas(boolean resuelta);
     NotificacionAveria getNotificacion(long id);
     void editNotificacion(NotificacionAveria notificacion);
     void deleteNotificacion(long id);
     void deleteNotificacion(NotificacionAveria notificacion);
     NotificacionAveria addNotificacion(NotificacionAveria notificacion);
 }
