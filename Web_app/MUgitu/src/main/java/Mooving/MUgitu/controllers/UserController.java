package Mooving.MUgitu.controllers;

import Mooving.MUgitu.dao.tipoUser.TipoUsuarioDao;
import Mooving.MUgitu.dao.user.UsuarioDao;
import Mooving.MUgitu.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    public final static int ENCRYPT_STRENGTH = 10;

    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    TipoUsuarioDao tipoUsuarioDao;

    private final RestTemplate restTemplate;


    public UserController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping(path = "/add")
    public String registerUser(Model model) {
        model.addAttribute("user", new Usuario());

        return "register";
    }

    @PostMapping(path="/add")
    public ModelAndView createNewUser (Model model,
                                       @ModelAttribute Usuario user,
                                       WebRequest request) {
        //Return to user form in case there is any error
        String returnStr = "register";
        String error = checkUserDuplicated(user);
        if (error.length() == 0) {
            if (!passwordsMatch(request)) {
                error = "Password mismatch";
            } else {
                returnStr = "/";
                BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder(ENCRYPT_STRENGTH);
                user.setPassword(encrypt.encode(request.getParameter("password")));

                String url = "localhost:8000/user/add";
                ResponseEntity<Usuario> userGuardado = restTemplate.postForEntity(url, user, Usuario.class);
                Usuario us = userGuardado.getBody();
            }
        }
        // Si se añade usuario notificar informacion sobre el
        // Sino, rellenar campos con los valores introducidos

        if (returnStr.equals("register")) {
            model.addAttribute("user", user);
            model.addAttribute("error", error);
        }

        return new ModelAndView(returnStr, new ModelMap(model));
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

    private boolean passwordsMatch(WebRequest request) {
        boolean match = true;

        String psw = request.getParameter("password");
        if (psw != null && !psw.equals(request.getParameter("passwordRep"))) {
            match = false;
        }
        return match;
    }
}
