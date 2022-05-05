package Mooving.MUgitu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Controller
public class HomeController implements ServletContextAware {
    public final static String BASE_PATH = "http://localhost:8000/MUgitu/REST/api";

    private ServletContext servletContext;

    public HomeController() {
    }

    @GetMapping(path = {"/", "/index", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/prueba")
    public String prueba() {
        System.out.println("alalala");
        return "login";
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
