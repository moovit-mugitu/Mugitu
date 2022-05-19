package Mooving.MUgituApi.dao.tipoUser;

import Mooving.MUgituApi.entities.TipoUsuario;

import java.util.List;

 public interface TipoUsuarioDao {
     List<TipoUsuario> getAllTipoUsuarios();
     TipoUsuario getTipoUsuario(long id);
     void editTipoUsuario(TipoUsuario tipoUsuario);
     void deleteTipoUsuario(long id);
     void deleteTipoUsuario(TipoUsuario tipoUsuario);
     TipoUsuario addTipoUsuario(TipoUsuario tipoUsuario);
 }
