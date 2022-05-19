package Mooving.MUgituApi.dao.estacion;


import Mooving.MUgituApi.entities.Estacion;

import java.util.List;

 public interface EstacionDao {
     List<Estacion> getAllEstacions();
     Estacion getEstacion(long id);
     void editEstacion(Estacion estacion);
     void deleteEstacion(long id);
     void deleteEstacion(Estacion estacion);
     Estacion addEstacion(Estacion estacion);
     List<Estacion> getEstacionesActivas(boolean activa);
 }
