package Mooving.MUgituApi.dao.averia;


import Mooving.MUgituApi.entities.Averia;

import java.util.List;

 public interface AveriaDao {
     List<Averia> getAllAverias();
     Averia getAveria(long id);
     void editAveria(Averia averia);
     void deleteAveria(long id);
     void deleteAveria(Averia averia);
     Averia addAveria(Averia averia);
     List<Averia> getAveriaByTipo(Integer tipo);
 }
