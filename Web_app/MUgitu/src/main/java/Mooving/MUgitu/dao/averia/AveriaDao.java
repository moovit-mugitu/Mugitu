package Mooving.MUgitu.dao.averia;


import Mooving.MUgitu.entities.Averia;
import Mooving.MUgitu.entities.Averia;

import java.util.List;

 public interface AveriaDao {
     List<Averia> getAllAverias();
     Averia getAveria(long id);
     void editAveria(Averia averia);
     void deleteAveria(long id);
     void deleteAveria(Averia averia);
     void addAveria(Averia averia);
     List<Averia> getAveriaByTipo(Integer tipo);
 }
