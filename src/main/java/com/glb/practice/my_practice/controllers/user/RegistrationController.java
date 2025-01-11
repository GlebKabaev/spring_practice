package com.glb.practice.my_practice.controllers.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.repository.user.UserRepository;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping({"/register","/register/"})
@AllArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    @GetMapping({"/",""})
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Страница для регистрации
    }

    @PostMapping({"/new","/new/"})
    public String registerUser(@ModelAttribute("user") User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));  // Шифруем пароль
        //TODO подтвердите пароль
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/login";  // Перенаправление на страницу логина
    }
}

