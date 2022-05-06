package Mooving.MUgituApi.dao.estacionar;

import Mooving.MUgituApi.entities.Estacionar;

import java.util.List;

 public interface EstacionarDao {
     List<Estacionar> getAllEstacionars();
     Estacionar getEstacionar(long id);
     void editEstacionar(Estacionar estacionar);
     void deleteEstacionar(long id);
     void deleteEstacionar(Estacionar estacionar);
     void addEstacionar(Estacionar estacionar);
 }