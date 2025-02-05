package com.glb.practice.my_practice.controllers.image;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glb.practice.my_practice.srevice.image.ImageService;


@Controller
@RequestMapping({ "/admin/image"})
@AllArgsConstructor
public class ImageController {
    ImageService imageService;
    @GetMapping("")
    public String getMethodName(@RequestParam String param) {
        return "";
    }
    
}
