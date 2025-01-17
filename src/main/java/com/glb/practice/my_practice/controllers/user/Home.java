package com.glb.practice.my_practice.controllers.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.user.UserService;

import lombok.AllArgsConstructor;


@Controller
@RequestMapping({"/",""})
@AllArgsConstructor
public class home {
    BookService bookService;
    UserService userService;
    @GetMapping("")
    public String defaultPage(@RequestParam String param) {
        return "redirect:/home/"; 
    }
    
    @GetMapping({"/home","/home/"})
    public String getMain(Model model){
      List<String> sortFields=Arrays.asList("Название","Автор","Депозит","Стоимость аренды");
      UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findByUserName(userDetails.getUsername());
        Reader reader = user.getReader();
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("books", bookService.getBooks("id"));
        model.addAttribute("username",reader.getFirstName()+" "+reader.getLastName());
        return "goods_list";
    }
    @GetMapping("/sort")
    public String sortBooks(@RequestParam("field") String field, Model model) { 
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> sortFields=Arrays.asList("Название","Автор","Депозит","Стоимость аренды");
        User user = userService.findByUserName(userDetails.getUsername());
        Reader reader = user.getReader();
        switch (field) {
            case "Название":
                field= "title";
                break;
            case "Автор":
                field= "author";
                break;
            case "Депозит":
                field="depositAmount";
                break;
            case "Стоимость аренды":
                field="rentalCost";
            default:
                break;
        }
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("books", bookService.getBooks(field));
        model.addAttribute("username",reader.getFirstName()+" "+reader.getLastName());
        return "goods_list";
        
    }
}