package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.TipoUsuario;
import Mooving.MUgituApi.entities.Usuario;
import Mooving.MUgituApi.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserApi {

    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    TipoUsuarioDao tipoUsuarioDao;

    public UserApi() {
    }

    @PostMapping(path="/add")
    public Usuario createNewUser (Usuario user) {
        user.setTipo_usuario(tipoUsuarioDao.getTipoUsuario(TipoUsuario.USER));
        usuarioDao.addUser(user);

        return user;
    }
}
