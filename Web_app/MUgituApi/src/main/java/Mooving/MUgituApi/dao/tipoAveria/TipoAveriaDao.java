package Mooving.MUgituApi.dao.tipoAveria;

import Mooving.MUgituApi.entities.TipoAveria;

import java.util.List;

 public interface TipoAveriaDao {
     List<TipoAveria> getAllTipoAverias();
     TipoAveria getTipoAveria(long id);
     void editTipoAveria(TipoAveria tipoAveria);
     void deleteTipoAveria(long id);
     void deleteTipoAveria(TipoAveria tipoAveria);
     void addTipoAveria(TipoAveria tipoAveria);
 }
