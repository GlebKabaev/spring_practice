package com.glb.practice.my_practice.controllers.user;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.service.reader.ReaderService;
import com.glb.practice.my_practice.service.user.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping({"/profile/","/profile"})
@AllArgsConstructor
public class ProfileController {
    UserService userService;
    ReaderService readerService;
    @GetMapping({"","/"})
    public String getMethodName(Model model) {
        Reader reader = readerService.thisReader();
        model.addAttribute("reader", reader);
        return "profile";
    }
    @GetMapping({"/edit","/edit/"})
    public String editReader( Model model) {
        Reader reader = readerService.thisReader();
        model.addAttribute("reader", reader);
        return "reader_self_edit";
    }
    @PostMapping({"/update_reader","/update_reader/"})
    public String updateReader(@ModelAttribute("reader") Reader reader, Model model) {
    
        try{
            readerService.updateReader(reader); 
            }catch(Exception e){
                e.printStackTrace();
                model.addAttribute("reader", reader);
                model.addAttribute("error", e.getMessage());
                return "reader_self_edit";
            }
            return "redirect:/profile"; 
    }

    
}
