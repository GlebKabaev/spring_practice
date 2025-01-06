package com.glb.practice.my_practice.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping({"/login","/login/"})
@AllArgsConstructor
public class LoginController {

    @GetMapping({"/",""})
    public String login() {
        return "login";  
    }
}
