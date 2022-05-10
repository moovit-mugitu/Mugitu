package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    public UserController() {
    }

    @GetMapping(path = "/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new Usuario());

        return "register";
    }

    @GetMapping(path = "/pruebaPermisos")
    @ResponseBody
    public String pruebaREST() {
        String respuesta  = RestRequests.RESTgetRequest("/user/pruebaPermisos",String.class);

        return respuesta;
    }

    @PostMapping(path = "/register")
    public ModelAndView createNewUser (Model model,
                                       @ModelAttribute Usuario user,
                                       WebRequest request) {
        String returnStr = "/";
        String error;
        if (!passwordsMatch(request)) {
            error = "Password mismatch";
        }
        else{
            error = RestRequests.RESTpostRequest("/user/register", user, String.class);
        }
        if(error != null && error.length() != 0){
            returnStr = "/register";
            model.addAttribute("user", user);
            model.addAttribute("error", error);
        }

        return new ModelAndView(returnStr, new ModelMap(model));
    }

    @GetMapping(path = "/all")
    public List<Usuario> getAllUsers() {
        Usuario[] respuesta  = RestRequests.RESTgetRequest("/user/all",Usuario[].class);

        return new ArrayList<>(Arrays.asList(respuesta));
    }
    @GetMapping(path = "/id/{id}")
    public Usuario getUserById(@PathVariable("id") long id) {
        Usuario respuesta  = RestRequests.RESTgetRequest("/user/id/"+id,Usuario.class);

        return respuesta;
    }
    @GetMapping(path = "/email/{email}")
    public Usuario getUserByEmail(@PathVariable("email") String email) {
        Usuario respuesta  = RestRequests.RESTgetRequest("/user/email/"+email,Usuario.class);

        return respuesta;
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
