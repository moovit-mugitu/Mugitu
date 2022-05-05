package Mooving.MUgituApi.dao.user;


import Mooving.MUgituApi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario getUsuarioByCorreo(String email);
    Usuario getUsuarioByDNI(String DNI);


}
