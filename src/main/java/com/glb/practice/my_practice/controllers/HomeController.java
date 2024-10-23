package com.glb.practice.my_practice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8 };
        model.addAttribute("numbers", numbers);
        return "first-view"; // Имя HTML-шаблона
    }
}
