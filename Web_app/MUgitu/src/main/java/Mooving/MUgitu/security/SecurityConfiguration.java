package Mooving.MUgitu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public final static int ENCRYPT_STRENGTH = 10;

    private final static String[] ADMIN_GET_MATCHERS = {"/user/all", "/bike/edit/**", "/estacion/edit/**",
            "/averia/edit/**"};
    private final static String[] ADMIN_POST_MATCHERS = {"/bike/edit/**", "/estacion/edit/**",
            "/averia/edit/**"};

    private final static String[] USER_GET_MATCHERS = {"/user/email/**", "/user/id/**", "/mainPage/**", "bike/**"};
    private final static String[] USER_POST_MATCHERS = {""};

    private final static String[] AUTHENTICATED_GET_MATCHERS = {""};
    private final static String[] AUTHENTICATED_POST_MATCHERS = {""};

    private final static String[] EVERYONE_GET_MATCHERS = {"/error", "/css/**", "/images/**", "/js/**",
            "/","/index","/home","/login", "/user/register", "/mainPage"};
    private final static String[] EVERYONE_POST_MATCHERS = {"/login", "/user/register"};

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
                .antMatchers(POST, ADMIN_POST_MATCHERS).hasRole("ADMIN")
                .antMatchers(GET, USER_GET_MATCHERS).hasAnyRole("USER", "ADMIN")
                //.antMatchers(POST, USER_POST_MATCHERS).hasRole("USER", "ADMIN")
                .antMatchers(GET, EVERYONE_GET_MATCHERS).permitAll()
                .antMatchers(POST, EVERYONE_POST_MATCHERS).permitAll()
                .anyRequest().authenticated();

        //Login control
        http.formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/mainPage")
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
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
