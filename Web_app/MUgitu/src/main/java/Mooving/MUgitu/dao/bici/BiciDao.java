package Mooving.MUgitu.dao.bici;


import Mooving.MUgitu.entities.Bici;

import java.util.List;

 public interface BiciDao {
     List<Bici> getAllBicis();
     Bici getBici(long id);
     void editBici(Bici bici);
     void deleteBici(long id);
     void deleteBici(Bici bici);
     void addBici(Bici bici);
     List<Bici> getBiciByElectrica(Boolean electrica);
 }
