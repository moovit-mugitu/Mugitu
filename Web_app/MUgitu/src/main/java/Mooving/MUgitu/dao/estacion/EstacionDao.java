package Mooving.MUgitu.dao.estacion;


import Mooving.MUgitu.entities.Estacion;

import java.util.List;

 public interface EstacionDao {
     List<Estacion> getAllEstacions();
     Estacion getEstacion(long id);
     void editEstacion(Estacion estacion);
     void deleteEstacion(long id);
     void deleteEstacion(Estacion estacion);
     void addEstacion(Estacion estacion);
     List<Estacion> getEstacionesActivas(boolean activa);
 }
