package Mooving.MUgitu.controllers;

import Mooving.MUgitu.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController implements ServletContextAware {

    private ServletContext servletContext;
    @Autowired
    private AuthenticationManager authenticationManager;

    public HomeController() {
    }

    @GetMapping(path = {"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("navPage", "index");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model, Authentication authentication) {
        MyUserDetails u = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("user", u.getUser());
        return "mainPage";
    }

    @GetMapping(path = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity<Void> result = RestRequests.RESTgetRequestResponseEntity("/token/refresh", Void.class);
        result.getHeaders();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
