package Mooving.MUgitu.security;

import Mooving.MUgitu.controllers.RestRequests;
import Mooving.MUgitu.entities.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Post de username y password
        //Recibes Token de login si entras bien
        //Con el token haces GET de tu usuario
        //Se lo guardas a MyUserDetails
        Usuario user = RestRequests.RESTgetRequest("/user/email/"+username, Usuario.class);//Username = email
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(user);
    }
}
