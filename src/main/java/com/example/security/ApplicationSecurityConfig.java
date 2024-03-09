package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableWebSecurity // adnotacja określa, że poniższa klasa będzie zawierała konfigurację dla 'Security'
@EnableGlobalMethodSecurity(prePostEnabled = true) // niezbędne by działały adnotacje nad metodami
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and() //Get działa poprawnie ale blokuje Post
                .csrf().disable() // będziemy wyłączać w celu zaprezentowania działania
                .authorizeRequests() // Wybieramy, że żadanie musi być autoryzowane (czyli odpowiednie prawa dostępu do zasobu)
                .antMatchers("/", "/css/*") // biała lista elementów nie wymagający logowania
                .permitAll() // każdy bez względu na uprawniania ma dostęp
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers("/management/api/**").permitAll() // kolejność dodawania warunków ma znaczenie
//                .antMatchers(HttpMethod.POST,"/management/api/v1/students").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/v1/students").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/management/api/v1/students").hasAnyAuthority(COURSE_READ.getPermission(), STUDENT_READ.getPermission())
                .anyRequest() // określa, że dla każdego żądania ma być to co poniżej
                .authenticated() // musi przejść autentykację (czyli klient musi podać login i hasło, które są w systemie)
                .and()
                .httpBasic();
    }

    @Override
    @Bean // Metoda zwraca obiekt będący Bean-em i będzie zarządzany przez kontekst Springa
    protected UserDetailsService userDetailsService() {
        UserDetails studentUser = User.builder()
                .username("student")
//                .password("123456") // omówimy sobie błąd związany z brakiem implementacji kodera
                .password(passwordEncoder.encode("123456")) // omówimy sobie błąd związany z brakiem implementacji kodera
//                .password(new BCryptPasswordEncoder(10).encode("123456")) // omówimy sobie błąd związany z brakiem implementacji kodera
//                .roles("STUDENT") // sekcja "ROLE_" jest dodawana automatycznie
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456")) // omówimy sobie błąd związany z brakiem implementacji kodera
//                .roles("ADMIN") // sekcja "ROLE_" jest dodawana automatycznie
//                .roles(ADMIN.name()) // sekcja "ROLE_" jest dodawana automatycznie
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails adminTraineeUser = User.builder()
                .username("admintrainee")
                .password(passwordEncoder.encode("123456")) // omówimy sobie błąd związany z brakiem implementacji kodera
//                .roles("ADMIN") // sekcja "ROLE_" jest dodawana automatycznie
//                .roles(ADMINTRAINEE.name()) // sekcja "ROLE_" jest dodawana automatycznie
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(
                studentUser,
                adminUser,
                adminTraineeUser);
    }
}
