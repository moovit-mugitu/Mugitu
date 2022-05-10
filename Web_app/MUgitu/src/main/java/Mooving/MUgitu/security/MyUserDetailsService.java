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
        /*HttpServletRequest request = RestRequests.getRequest();
        String accessToken = (String) request.getParameter("accessToken");
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, RestRequests.TokenPrefix + accessToken);

        //Request to Rest Api app
        ResponseEntity<Usuario> response = RestRequests.RestRequestWithHeaders(
                "/user/email/"+username, HttpMethod.GET, headers, Usuario.class);

        //Check status
        if(response.getBody() == null || response.getStatusCode() != HttpStatus.OK){
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(response.getBody());*/

        HttpServletRequest request = RestRequests.getRequest();
        String password = (String) request.getParameter("password");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("password", password);
        map.add("username", username);
        String responseToken = RestRequests.RESTpostRequestForm("/login", map, String.class);
        JSONObject jsonObj = null;
        String accessToken ="", refreshToken = "";
        try {
            jsonObj = new JSONObject(responseToken);
            accessToken = jsonObj.get("accessToken").toString();
            refreshToken = jsonObj.get("refreshToken").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpSession session = RestRequests.getSession();
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, RestRequests.TokenPrefix + accessToken);
        ResponseEntity<Usuario> responseUser = RestRequests.RestRequestWithHeaders(
                "/user/email/"+username, HttpMethod.GET, headers, Usuario.class);
        if(responseUser.getBody() == null || responseUser.getStatusCode() != HttpStatus.OK){
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(responseUser.getBody());
    }
}
