package com.glb.practice.my_practice.config;

import com.glb.practice.my_practice.srevice.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Используем BCrypt для паролей
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        // Настройка аутентификации через наш UserDetailsService
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                                     .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();  // Теперь вызываем build() на AuthenticationManagerBuilder
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register","/register/new").permitAll()  // Страница логина и регистрации открыты
                .anyRequest().authenticated()  // Все остальные страницы требуют аутентификацию
            )
            .formLogin(form -> form
                .loginPage("/login")  // Страница логина
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()  // Страница логаута доступна всем
            );
        return http.build();  // Возвращаем SecurityFilterChain
    }
}