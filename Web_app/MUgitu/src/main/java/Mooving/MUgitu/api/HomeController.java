package Mooving.MUgitu.api;

import Mooving.MUgitu.dao.estacion.EstacionDao;
import Mooving.MUgitu.dao.estacion.EstacionRepository;
import Mooving.MUgitu.entities.Estacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Controller
public class HomeController implements ServletContextAware {

    private ServletContext servletContext;
    @Autowired
    private EstacionDao estacionDao;

    public HomeController() {
    }

    @GetMapping(path = {"/", "/index", "/home"})
    public String home() {
        Estacion estacion = estacionDao.getEstacion(1L);
        System.out.println(estacion.getLatitud() + " " + estacion.getLongitud());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "ADMIN";
    }

    @GetMapping("/user")
    public String user() {
        return "USER";
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
