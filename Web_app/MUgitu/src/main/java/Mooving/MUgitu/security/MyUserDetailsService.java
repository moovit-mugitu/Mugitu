package Mooving.MUgitu.security;

import Mooving.MUgitu.controllers.RestRequests;
import Mooving.MUgitu.entities.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Transactional
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = RestRequests.getRequest();
        String password = (String) request.getParameter("password");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("password", password);
        map.add("username", username);
        String responseToken = RestRequests.RESTpostRequestForm("/login", map, String.class);
        JSONObject jsonObj;
        String accessToken ="", refreshToken = "";
        try {
            jsonObj = new JSONObject(responseToken);
            accessToken = jsonObj.get(RestRequests.ACCESSTOKEN).toString();
            refreshToken = jsonObj.get(RestRequests.REFRESHTOKEN).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpSession session = RestRequests.getSession();
        session.setAttribute(RestRequests.ACCESSTOKEN, accessToken);
        session.setAttribute(RestRequests.REFRESHTOKEN, refreshToken);

        ResponseEntity<Usuario> responseUser = RestRequests.RestRequestWithHeaders(
                "/user/email/"+username, HttpMethod.GET, accessToken, Usuario.class);

        if(responseUser.getBody() == null || responseUser.getStatusCode() != HttpStatus.OK){
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(responseUser.getBody());
    }
}
