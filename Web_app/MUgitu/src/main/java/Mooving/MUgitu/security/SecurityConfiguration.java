package Mooving.MUgitu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static int REMEMBER_ME_TIME = 86400;  //1 day
    public final static int ENCRYPT_STRENGTH = 10;
    private final static String[] ADMIN_MATCHERS = {"/admin"};
    private final static String[] USER_MATCHERS = {"/user"};

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //Filter pages based on the authority or role the user has
                .antMatchers(ADMIN_MATCHERS).hasRole("ADMIN")
                .antMatchers(USER_MATCHERS).hasRole("USER")
                .anyRequest().permitAll()
                .and()
                //Login control
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login_process")
                .usernameParameter("txtUsername")
                .passwordParameter("txtPassword")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .and()
                //Logout control
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and()
                //Login remember me control
                .rememberMe()
                .tokenValiditySeconds(REMEMBER_ME_TIME).key("MUgituKey")
                .rememberMeParameter("checkRememberMe");
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCRYPT_STRENGTH);
    }
}
