package com.glb.practice.my_practice.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.service.reader.ReaderService;
import com.glb.practice.my_practice.service.user.UserService;

import lombok.AllArgsConstructor;
//TODO: делегировать бизнес логику на сервис
@Controller
@RequestMapping({ "/register", "/register/" })
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ReaderService readerService;

    @GetMapping({ "/", "" })
    public String register(Model model) {
        model.addAttribute("reader", new Reader());
        model.addAttribute("user", new User());
        return "register"; // Страница для регистрации
    }

    @PostMapping({ "/new", "/new/" })
    public String registerUser(@ModelAttribute("user") User user, @ModelAttribute("reader") Reader reader, Model model,
            @RequestParam("confirmPassword") String confirmPassword) {
        boolean isUserCreated;
        user.setRole("ROLE_USER");
        try {
            if (user.getPassword().equals(confirmPassword)) {
                userService.saveUser(user);
                isUserCreated = true;
            } else {
                throw new Exception("поле подтверждения пароля не совпадает");
            }
        } catch (Exception e) {
            isUserCreated = false;
            model.addAttribute("user", user);
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                model.addAttribute("error",
                        "Пользователь с таким логином уже существует. Пожалуйста, укажите другой логин.");
            } else {
                model.addAttribute("error", e.getMessage());
            }
            return "register";
        }
        if (isUserCreated) {
            try {
                readerService.save(reader);
                userService.addReaderForUser(user, reader);
            } catch (Exception e) {
                e.printStackTrace();

                model.addAttribute("reader", reader);
                if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                    model.addAttribute("error",
                            "Пользователь с таким номером телефона уже существует. Пожалуйста, укажите другой номер.");
                } else {
                    model.addAttribute("error", e.getMessage());
                }
                userService.deleteUser(user.getId());
                return "register";
            }
            return "redirect:/login"; // Перенаправление на страницу логина
        } else
            return "register";
    }
}
