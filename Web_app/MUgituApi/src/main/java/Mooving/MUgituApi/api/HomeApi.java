package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.entities.Usuario;
import Mooving.MUgituApi.security.MyAuthenticationFilter;
import Mooving.MUgituApi.security.MyAuthorizationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class HomeApi {

    @Autowired
    UsuarioDao usuarioDao;

    @GetMapping(path = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith(MyAuthorizationFilter.HeaderPrefix)){
            try {
                String refreshToken = authorizationHeader.substring(MyAuthorizationFilter.HeaderPrefix.length());
                Algorithm algorithm = Algorithm.HMAC256(MyAuthenticationFilter.AlgorithmKey);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Usuario user = usuarioDao.getUsuarioByEmail(username);
                String accessToken = JWT.create()
                        .withSubject(user.getCorreo())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))   //Dura 10 mins
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(MyAuthenticationFilter.RolesString, new ArrayList<String>().add( user.getTipo_usuario().getDescripcion()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else{
            throw new RuntimeException("Refresh Token is missing");
        }
    }
}
