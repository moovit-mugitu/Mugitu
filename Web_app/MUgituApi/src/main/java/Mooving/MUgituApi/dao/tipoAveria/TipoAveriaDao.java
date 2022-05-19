package Mooving.MUgituApi.dao.tipoAveria;

import Mooving.MUgituApi.entities.TipoAveria;

import java.util.List;

 public interface TipoAveriaDao {
     List<TipoAveria> getAllTipoAverias();
     TipoAveria getTipoAveria(int id);
     void editTipoAveria(TipoAveria tipoAveria);
     void deleteTipoAveria(int id);
     void deleteTipoAveria(TipoAveria tipoAveria);
     TipoAveria addTipoAveria(TipoAveria tipoAveria);
 }
