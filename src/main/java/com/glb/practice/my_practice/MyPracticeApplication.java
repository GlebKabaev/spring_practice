package com.glb.practice.my_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@SpringBootApplication
public class MyPracticeApplication {
    public static void main(String[] args) {SpringApplication.run(MyPracticeApplication.class, args);}
    // //авторизация и генерация пароля user в системе
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    //     return http.httpBasic().and()
    //     .authorizeHttpRequests()
    //     .anyRequest().authenticated().and()
    //     .build();
    // }
    
}