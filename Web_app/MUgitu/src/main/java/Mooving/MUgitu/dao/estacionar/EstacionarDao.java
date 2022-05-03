package Mooving.MUgitu.dao.estacionar;

import Mooving.MUgitu.entities.Estacionar;

import java.util.List;

 public interface EstacionarDao {
     List<Estacionar> getAllEstacionars();
     Estacionar getEstacionar(long id);
     void editEstacionar(Estacionar estacionar);
     void deleteEstacionar(long id);
     void deleteEstacionar(Estacionar estacionar);
     void addEstacionar(Estacionar estacionar);
 }
