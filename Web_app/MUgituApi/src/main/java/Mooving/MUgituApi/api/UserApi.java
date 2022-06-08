package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.TipoUsuario;
import Mooving.MUgituApi.entities.Usuario;
import Mooving.MUgituApi.security.MyUserDetails;
import Mooving.MUgituApi.security.SecurityConfiguration;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserApi {

    final UsuarioDao usuarioDao;
    final TipoUsuarioDao tipoUsuarioDao;

    public UserApi(UsuarioDao usuarioDao, TipoUsuarioDao tipoUsuarioDao) {
        this.usuarioDao = usuarioDao;
        this.tipoUsuarioDao = tipoUsuarioDao;
    }

    ///  GET METHODS  ///

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<Usuario> getUserByEmail(@PathVariable("email") String mail, Authentication authentication) {
        Usuario user = getPrincipal(usuarioDao, authentication);
        if (checkPermission(user, mail, "mail")) {
            Usuario usuario = usuarioDao.getUsuarioByEmail(mail);
            if (usuario == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable("id") long id, Authentication authentication) {
        Usuario user = getPrincipal(usuarioDao, authentication);
        if (checkPermission(user, String.valueOf(id), "id")) {
            Usuario usuario = usuarioDao.getUser(id);
            if (usuario == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> usuarios = usuarioDao.getAllUsers();
        if (usuarios == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuarios);
    }

    ///  POST METHODS  ///

    @PostMapping(path = "/register")
    public ResponseEntity<String> createNewUser(@RequestBody Usuario usuario) {
        String error = checkUserDuplicated(usuario);
        usuario.setTipo_usuario(tipoUsuarioDao.getTipoUsuario(2));

        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder(SecurityConfiguration.ENCRYPT_STRENGTH);
        usuario.setPassword(encrypt.encode(usuario.getPassword()));
        if (error.length() == 0) usuarioDao.addUser(usuario);

        return ResponseEntity.ok(error);
    }

    ///  DELETE METHODS  ///

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> createBici(@PathVariable("id") long id) {
        try {
            usuarioDao.deleteUser(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }


    //Verification functions
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

    private boolean checkPermission(Usuario applicant, String requestedUser, String identifier) {
        boolean permited = false;
        switch (identifier) {
            case "id":
                if (applicant.getUserId().equals(Long.valueOf(requestedUser)) ||
                        applicant.getTipo_usuario().getDescripcion().equals("ADMIN")) {
                    permited = true;
                }
                break;
            case "mail":
                if (applicant.getCorreo().equals(requestedUser) ||
                        applicant.getTipo_usuario().getDescripcion().equals("ADMIN")) {
                    permited = true;
                }
                break;
        }
        return permited;
    }

    public static Usuario getPrincipal(UsuarioDao usuarioDao, Authentication authentication) {
        return usuarioDao.getUsuarioByEmail((String) authentication.getPrincipal());
    }
}
