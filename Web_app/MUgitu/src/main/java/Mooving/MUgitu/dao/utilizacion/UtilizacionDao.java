package Mooving.MUgitu.dao.utilizacion;

import Mooving.MUgitu.entities.Utilizacion;

import java.util.List;

 public interface UtilizacionDao {
     List<Utilizacion> getAllUtilizacions();
     Utilizacion getUtilizacion(long id);
     void editUtilizacion(Utilizacion utilizacion);
     void deleteUtilizacion(long id);
     void deleteUtilizacion(Utilizacion utilizacion);
     void addUtilizacion(Utilizacion utilizacion);
 }
