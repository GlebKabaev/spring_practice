package com.glb.practice.my_practice.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TasksController {
    @GetMapping("/tasks")
    public String tasks() {
        return "tasks-view"; // Имя другого HTML-шаблона
    }
}
