package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.TipoUsuario;
import Mooving.MUgituApi.entities.Usuario;
import Mooving.MUgituApi.security.SecurityConfiguration;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    TipoUsuarioDao tipoUsuarioDao;

    public UserApi() {
    }

    @GetMapping(path="/email/{email}")
    public ResponseEntity<Usuario>  getUserByEmail(@PathVariable("email") String mail) {
        Usuario usuario = usuarioDao.getUsuarioByEmail(mail);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping(path="/id/{id}")
    public ResponseEntity<Usuario> getUserById (@PathVariable("id") long id) {
        Usuario usuario = usuarioDao.getUser(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping(path="/all")
    public ResponseEntity<List<Usuario>> getAllUsers () {
        List<Usuario> usuario = usuarioDao.getAllUsers();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping(path="/register")
    public ResponseEntity<String> createNewUser (@RequestBody Usuario usuario) {
        String error = checkUserDuplicated(usuario);

        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder(SecurityConfiguration.ENCRYPT_STRENGTH);
        usuario.setPassword(encrypt.encode(usuario.getPassword()));
        if(error.length()==0)usuarioDao.addUser(usuario);

        return ResponseEntity.ok(error);
    }

    @PostMapping(path="/guardarAlgo")
    public ResponseEntity<?> guardadYDevolverVOid (@RequestBody CustomEntity customEntity) {
        //Codigooo
        return ResponseEntity.ok().build();
    }

    @Data
    class CustomEntity{
        String name;
        String surname;
    }

    private String checkUserDuplicated(Usuario user) {
        String errorStr = "User already exists: ";
        if (usuarioDao.getUsuarioByEmail(user.getCorreo()) != null) {
            errorStr += "Email already in use";
        } else if (usuarioDao.getUserByDNI(user.getDNI()) != null) {
            errorStr += "DNI already in use";
        } else {
            errorStr = "";
        }
        return errorStr;
    }
}
