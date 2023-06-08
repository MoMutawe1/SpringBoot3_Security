package springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // we use @EnableMethodSecurity: to achieve the authorization and enable using the @PreAuthorize in our controller class as a method level annotation.
public class SecurityConfig {

    //authentication (to implement authentication we use UserDetailsService interface from spring security)
    @Bean
    public UserDetailsService userDetailsService() {
        //Hardcoding user details VS getting info from DB.

        //UserDetails admin = User.withUsername("Mohammad")
        //        .password(encoder.encode("Pwd1"))         // it's not recommended to keep the password in plain text, so we use PasswordEncoder to encript the password before saving it to DB.
        //        .roles("ADMIN")
        //        .build();
        //UserDetails user = User.withUsername("Suna")
        //       .password(encoder.encode("Pwd2"))
        //        .roles("USER","ADMIN","HR")
        //        .build();
        //return new InMemoryUserDetailsManager(admin, user);   // to save hardcoded user credentials in memory
        return new UserInfoUserDetailsService();
    }

    //authorization (to implement authorization we use SecurityFilterChain interface from spring security)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/products/welcome","/products/new").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/products/**")
                .authenticated().and().formLogin().and().build();  // authenticated() means user need to be logged in before accessing the recourse.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}