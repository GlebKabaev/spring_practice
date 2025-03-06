package com.glb.practice.my_practice.controllers.user;

import org.springframework.web.bind.annotation.RestController;



import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Map;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;



@RestController
@AllArgsConstructor
public class HelloUserRestController {
    
    @GetMapping("/api/v1/greetings")
    @ResponseBody
    public Map<String,String>  getHello1(){

        
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Map.of("greetings", "   Hello, %s".formatted(userDetails.getUsername()));
    }
    @GetMapping("/api/v2/greetings")
    public ResponseEntity <Map<String,String>> getHello2() {
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON).body(Map.of("greeting","Hello, %s".formatted(userDetails.getUsername())));
    }
    @GetMapping("/api/v3/greetings")
    public ResponseEntity <Map<String,String>> getHello3(HttpServletRequest request) {
        UserDetails userDetails =(UserDetails) (((Authentication) request.getUserPrincipal())).getPrincipal();
        Map<String, String> response = Map.of(
            "name1", userDetails.getUsername(),
            "name2", userDetails.getUsername()
            );
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }
    @GetMapping("/api/v4/greetings")
    public ResponseEntity <Map<String,String>> getHello4(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> response = Map.of(
            "name1", userDetails.getUsername(),
            "name2", userDetails.getUsername()
            );
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }

}
