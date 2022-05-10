package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Usuario;
import Mooving.MUgitu.security.MyUserDetails;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@Controller
public class HomeController implements ServletContextAware {

    private ServletContext servletContext;
    @Autowired
    private AuthenticationManager authenticationManager;

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

    @GetMapping("/prueba")
    public void prueba() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
    }

    /*@PostMapping("/login")
    public String loginPost(WebRequest request) throws JSONException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("password", password);
        map.add("username", username);

        //Obtain token for Rest app
        String response = RestRequests.RESTpostRequestForm("/login", map, String.class);
        JSONObject jsonObj = new JSONObject(response);
        String accessToken = jsonObj.get("accessToken").toString();
        String refreshToken = jsonObj.get("refreshToken").toString();
        HttpSession session = RestRequests.getSession();
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);
        map.add("accessToken", accessToken);

        //Login in local app
        //ResponseEntity<String> responseLocal = RestRequests.localPostRequestForm("/login_process", map, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, RestRequests.TokenPrefix + accessToken);
        ResponseEntity<Usuario> responseEntity = RestRequests.RestRequestWithHeaders(
                "/user/email/"+username, HttpMethod.GET, headers, Usuario.class);

        if(responseEntity.getBody() != null && responseEntity.getStatusCode() == HttpStatus.OK){
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(username, password);
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);



            MyUserDetails userDetails = new MyUserDetails(responseEntity.getBody());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
        }
        return "mainPage";
    }*/

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
