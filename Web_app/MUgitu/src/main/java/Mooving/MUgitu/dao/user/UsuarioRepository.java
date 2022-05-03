package Mooving.MUgitu.dao.user;


import Mooving.MUgitu.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario getUsuarioByCorreo(String email);
    Usuario getUsuarioByDNI(String DNI);


}
