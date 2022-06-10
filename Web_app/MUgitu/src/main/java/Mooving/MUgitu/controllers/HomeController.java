package mooving.mugitu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = {"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("navPage", "index");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
