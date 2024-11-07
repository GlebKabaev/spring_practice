package com.glb.practice.my_practice.controllers.user;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.srevice.user.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserViewController {
    private final UserService userService;
    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
    return "user_list";
    }
    @GetMapping("/{id}")
    public String showUserData(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.findByIDUser(id));
        return "user";
    }
    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User()); 
    return "user_add-edit"; 
    }
    @PostMapping("/save_user")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user); 

        return "redirect:/users"; 
    }
    @GetMapping("/delete_user/{id}")
    public String deleteUser(Model model,@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findByIDUser(id));
        return "user_add-edit";
    }
    @PostMapping("/update_user")
    public String updateUser(@ModelAttribute("user") User user) {
    
    if (user.getId() != null && userService.findByIDUser(user.getId()) != null) {
        
        userService.updateUser(user);
    } else {
        
        throw new IllegalArgumentException("Пользователь с таким ID не найдена или ID не указан");
    }
        return "redirect:/users";
    }
}
