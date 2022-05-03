package Mooving.MUgitu.dao.user;


import Mooving.MUgitu.entities.Usuario;

import java.util.List;

 public interface UsuarioDao {
     List<Usuario> getAllUsers();
     
     Usuario getUser(long id);
     void editUser(Usuario user);
     void deleteUser(long id);
     void deleteUser(Usuario user);
     void addUser(Usuario user);
     Usuario getUserByDNI(String DNI);
     Usuario getUsuarioByEmail(String email);

 }
