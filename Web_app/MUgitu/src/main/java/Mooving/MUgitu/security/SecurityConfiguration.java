package Mooving.MUgitu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static int REMEMBER_ME_TIME = 86400;  //1 day
    public final static int ENCRYPT_STRENGTH = 10;

    private final static String[] ADMIN_GET_MATCHERS = {"/user/all"};
    private final static String[] USER_GET_MATCHERS = {""};
    private final static String[] AUTHENTICATED_GET_MATCHERS = {""};
    private final static String[] EVERYONE_GET_MATCHERS = {"/","/index","/home","/login", "/register"};

    private final static String[] ADMIN_POST_MATCHERS = {""};
    private final static String[] USER_POST_MATCHERS = {""};
    private final static String[] AUTHENTICATED_POST_MATCHERS = {""};
    private final static String[] EVERYONE_POST_MATCHERS = {"/login", "/register"};

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
                .anyRequest().permitAll();

        //Creation of JWT...
        http.addFilter(new MyAuthenticationFilter(authenticationManagerBean()));


        //Login control
        http.formLogin()
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
        //Enable all types POST, PUT...
                .and()
                .cors().and().csrf().disable();
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(ENCRYPT_STRENGTH);
    }
}