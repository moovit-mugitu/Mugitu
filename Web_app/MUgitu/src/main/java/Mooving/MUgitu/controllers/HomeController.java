package Mooving.MUgitu.controllers;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class HomeController implements ServletContextAware {

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

    @PostMapping("/login")
    public String loginPost(WebRequest request, HttpSession session) throws JSONException {
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("password", password);
        map.add("username", username);

        String response = RestRequests.RESTpostRequestForm("/login", map, String.class);
        JSONObject jsonObj = new JSONObject(response);
        String accessToken = jsonObj.get("accessToken").toString();
        String refreshToken = jsonObj.get("refreshToken").toString();
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);

        String response2 = RestRequests.localPostRequestForm("/login_process", map, String.class);
        return response2;
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
