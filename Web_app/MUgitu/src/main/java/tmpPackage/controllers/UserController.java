package tmpPackage.controllers;

import tmpPackage.entities.Usuario;
import tmpPackage.security.MyUserDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping(path = "/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new Usuario());

        return "register";
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
    public String getAllUsers(Model model) {
        ResponseEntity<Usuario[]> response = RestRequests.RestRequestWithHeaders(
                "/user/all", HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Usuario[].class);

        List<Usuario> users = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        model.addAttribute("users", users);
        return "userView";
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    public Usuario getUserById(@PathVariable("id") long id) {
        ResponseEntity<Usuario> responseUser = RestRequests.RestRequestWithHeaders(
                "/user/id/"+id, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Usuario.class);

        return responseUser.getBody();
    }

    @GetMapping(path = "/profile")
    @ResponseBody
    public Usuario getUserById(Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }

    @GetMapping(path = "/email/{email}")
    @ResponseBody
    public Usuario getUserByEmail(@PathVariable("email") String email) {
        ResponseEntity<Usuario> responseUser = RestRequests.RestRequestWithHeaders(
                "/user/email/"+email, HttpMethod.GET, RestRequests.getToken(RestRequests.ACCESSTOKEN), Usuario.class);

        return responseUser.getBody();
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
