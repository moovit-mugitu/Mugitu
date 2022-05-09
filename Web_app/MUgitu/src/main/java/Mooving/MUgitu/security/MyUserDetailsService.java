package Mooving.MUgitu.security;

import Mooving.MUgitu.controllers.RestRequests;
import Mooving.MUgitu.entities.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.ocsp.Req;
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
        Usuario user = RestRequests.RESTgetRequest("/user/email/"+username, Usuario.class);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(user);
    }
}
