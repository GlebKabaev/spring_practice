package com.glb.practice.my_practice.controllers.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.service.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserViewController {
    UserService userService;

    @GetMapping({ "/", "" })
    public String showUsers(Model model) {
        List<String> sortFields = Arrays.asList("id", "username");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("users", userService.getUsers("id"));
        return "user_list";
    }

    @GetMapping("/sort")
    public String sortUsers(@RequestParam("field") String field, Model model) {
        List<String> sortFields = Arrays.asList("id", "username");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("users", userService.getUsers(field));
        return "user_list";
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public String showUserData(Model model, @PathVariable int id) {

        model.addAttribute("user", userService.findByIdUser(id));
        return "user";
    }

    @GetMapping({ "/new_admin", "/new_admin/" })
    public String showCreateAdminForm(Model model) {
        User admin = new User();
        admin.setRole("ROLE_ADMIN");
        model.addAttribute("user", admin);
        return "user_add-edit";
    }

    @GetMapping({ "/new_user", "/new_user/" })
    public String showCreateUserForm(Model model) {
        User user = new User();
        user.setRole("ROLE_USER");
        model.addAttribute("user", user);
        return "user_add-edit";
    }

    @PostMapping({ "/save_user", "/save_user/" })
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            model.addAttribute("user", user);
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                model.addAttribute("error",
                        "Пользователь с таким username уже существует. Пожалуйста, укажите другой username.");
            } else {
                model.addAttribute("error", e.getMessage());
            }
            return "user_add-edit";
        }
        return "redirect:/admin/users";
    }

    @DeleteMapping({ "/delete_user/{id}", "/delete_user/{id}/" })
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PatchMapping({ "/update_user", "/update_user/" })
    public String updateUser(@ModelAttribute("user") User user, Model model) {

        try {
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            model.addAttribute("error", e.getMessage());
            return "user_add-edit";
        }
        return "redirect:/admin/users";
    }

    @GetMapping({ "/edit/{id}", "/edit/{id}/" })
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findByIdUser(id));
        return "user_add-edit";
    }
}
