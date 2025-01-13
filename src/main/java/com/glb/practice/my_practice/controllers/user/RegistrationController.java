package com.glb.practice.my_practice.controllers.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.user.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping({"/register","/register/"})
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ReaderService readerService;
    @GetMapping({"/",""})
    public String register(Model model) {
        model.addAttribute("reader", new Reader());
        model.addAttribute("user", new User());
        return "register";  // Страница для регистрации
    }

    @PostMapping({"/new","/new/"})
    public String registerUser(@ModelAttribute("user") User user,@ModelAttribute("reader") Reader reader,Model model) {
        boolean isUserCreated;
        //TODO подтвердите пароль
        user.setRole("ROLE_USER");
        try{
        userService.saveUser(user);
        isUserCreated=true;
        }catch(Exception e){
            isUserCreated=false;
            model.addAttribute("user", user);
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                model.addAttribute("error", "Пользователь с таким логином уже существует. Пожалуйста, укажите другой логин.");
            } else {
                model.addAttribute("error",e.getMessage());
            }
            return "register";
        }
        if (isUserCreated){
        try{
            readerService.saveReader(reader); 
            user.setReader(reader);
            userService.addReaderForUser(user, reader);
            }catch(Exception e){
                e.printStackTrace();
                
                model.addAttribute("reader", reader);
                if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                    model.addAttribute("error", "Пользователь с таким номером телефона уже существует. Пожалуйста, укажите другой номер.");
                } else {
                    model.addAttribute("error",e.getMessage());
                }
                userService.deleteUser(user.getId());
                return "register";
            }
        return "redirect:/login";  // Перенаправление на страницу логина
        }else return "register";
    }
}

