package com.example.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //Wybieramy, że żądanie musi być autoryzowane (czyli posiadać prawo do zasobu)
                .antMatchers("/", "index.html") //biała list elementów nie wymagających logowania, elementy oddzielamy przcinkami
                .permitAll()//bez względu na uprawnienia ma dostęp
                .anyRequest() //określa że dla każdego żądania ma być 5to co ponizej
                .authenticated() //musi przejść autentykacje (czyli klient musi podać login i hasło)
                .and()
                .httpBasic();
    }
}
