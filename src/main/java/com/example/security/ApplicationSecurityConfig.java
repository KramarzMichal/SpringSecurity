package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //Wybieramy, że żądanie musi być autoryzowane (czyli posiadać prawo do zasobu)
                .antMatchers("/", "index.html") //biała list elementów nie wymagających logowania, elementy oddzielamy przcinkami
                .permitAll()//bez względu na uprawnienia ma dostęp
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest() //określa że dla każdego żądania ma być 5to co ponizej
                .authenticated() //musi przejść autentykacje (czyli klient musi podać login i hasło)
                .and()
                .httpBasic();
    }
    @Override
    @Bean //Oznacza, że metoda zwraca obiekt który jest Bean-em i będzie zarządzany przez kontekst Springa
    protected UserDetailsService userDetailsService() {
        UserDetails studentUser = User.builder()
                .username("student")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name())
                .build();
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(studentUser, adminUser);

    }
}
