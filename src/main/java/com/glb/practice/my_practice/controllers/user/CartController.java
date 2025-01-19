package com.glb.practice.my_practice.controllers.user;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;

import com.glb.practice.my_practice.srevice.cart.CartElementServiceImpl;
import com.glb.practice.my_practice.srevice.reader.ReaderService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping({"/cart/","/cart"})
@AllArgsConstructor
public class CartController {
    CartElementServiceImpl cartElementService;
    ReaderService readerService;
    @GetMapping({"","/"})
    public String getMethodName(Model model) {
        Reader reader = readerService.thisReader();
        List<CartElement> cartElements=cartElementService.getCartElementsByReaderId(reader.getId());
        model.addAttribute("cart_elements",cartElements);
        return "reader_cart";
    }
    @GetMapping({"/delete_element/{id}","/delete_element/{id}/"})
    public String deleteBook(Model model,@PathVariable int id) {
        CartElement cartElement=cartElementService.findByIDCartElement(id);
        Reader reader = readerService.thisReader();
        if (reader.getId().equals(cartElement.getReader().getId())){
        cartElementService.deleteCartElement(id);
        }
        return "redirect:/cart";
    }
}
