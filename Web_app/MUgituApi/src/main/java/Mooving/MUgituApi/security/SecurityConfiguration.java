package Mooving.MUgituApi.security;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public final static int ENCRYPT_STRENGTH = 10;
    private final static String[] ADMIN_GET_MATCHERS = {"/user/all/**", "/bici/parada", "/bici/ocupada", "/bici/generar"};
    private final static String[] ADMIN_DELETE_MATCHERS = {"/bici/delete/*", "/estacionar/delete/*", "/estacion/delete/*",
            "/evento/delete/*", "/notificacion/delete/*", "/tipoAveria/delete/*", "/tipoUsuario/delete/*",
            "/user/delete/**"};
    private static final String[] ADMIN_PUT_MATCHERS = {"/bici/edit/**", "/estacion/edit/**",
            "/estacionar/edit/**", "/tipoAveria/edit/**", "/estacion/create/**", "/user/**"};

    private final static String[] WORKER_GET_MATCHERS = {"/averia/**", "/notificacion/worker/**", "/notificacion/id/**", "/utilizar/all", "/utilizar/user/id/**"};
    private final static String[] WORKER_DELETE_MATCHERS = {"/averia/delete", "/utilizar/delete/*"};
    private static final String[] WORKER_PUT_MATCHERS = {"/averia/**", "/notificacion/edit/**"};

    private final static String[] USER_GET_MATCHERS = {"/user/email/**", "/user/id/**", "/bici/**", "/estacion/**",
            "/estacionar/**", "/api/biciEstacion", "/utilizar/user", "/ia/**","/notificacion/user/**", "/notificacion/id/**"};
    private final static String[] USER_PUT_MATCHERS = {"/notificacion/create", "/utilizar/create/**", "/estacionar/create/**"};


    private final static String[] EVERYONE_GET_MATCHERS = {"/token/refresh", "/tipoAveria/**", "/rabbitmq/**", "/estacion/**"};
    private final static String[] EVERYONE_POST_MATCHERS = {"/login", "/user/register", "/rabbitmq/**"};




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
                .antMatchers(GET, ADMIN_GET_MATCHERS).hasRole("ADMIN")
                .antMatchers(DELETE, ADMIN_DELETE_MATCHERS).hasRole("ADMIN")
                .antMatchers(PUT, ADMIN_PUT_MATCHERS).hasRole("ADMIN")

                .antMatchers(GET, WORKER_GET_MATCHERS).hasAnyRole("ADMIN", "WORKER")
                .antMatchers(DELETE, WORKER_DELETE_MATCHERS).hasAnyRole("ADMIN", "WORKER")
                .antMatchers(PUT, WORKER_PUT_MATCHERS).hasAnyRole("ADMIN", "WORKER")

                .antMatchers(GET, USER_GET_MATCHERS).hasAnyRole("USER", "WORKER", "ADMIN")
                .antMatchers(PUT, USER_PUT_MATCHERS).hasAnyRole("USER", "WORKER", "ADMIN")
                .antMatchers(GET, EVERYONE_GET_MATCHERS).permitAll()
                .antMatchers(POST, EVERYONE_POST_MATCHERS).permitAll()
                .anyRequest().denyAll();

        //Creation of JWT...
        http.addFilter(new MyAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


                //Login control
        http.formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/error?message=login error")
                .and()
                //Logout control
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
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
}
