package Mooving.MUgitu.dao.notificacionAveria;


import Mooving.MUgitu.entities.NotificacionAveria;

import java.util.List;

 public interface NotificacionDao {
     List<NotificacionAveria> getAllNotificacions();
     NotificacionAveria getNotificacion(long id);
     void editNotificacion(NotificacionAveria notificacion);
     void deleteNotificacion(long id);
     void deleteNotificacion(NotificacionAveria notificacion);
     void addNotificacion(NotificacionAveria notificacion);
 }
