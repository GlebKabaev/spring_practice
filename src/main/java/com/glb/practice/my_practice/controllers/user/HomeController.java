package com.glb.practice.my_practice.controllers.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;

import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.cart.CartElementService;
import com.glb.practice.my_practice.srevice.reader.ReaderService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping({ "/", "" })
@AllArgsConstructor
public class HomeController {
    private BookService bookService;
    private CartElementService cartElementService;
    private ReaderService readerService;
    
    @GetMapping("")
    public String defaultPage(@RequestParam String param) {
        return "redirect:/home/";
    }

    @GetMapping({ "/home", "/home/" })
    public String sortBooks(@RequestParam(value = "field", required = false, defaultValue = "title") String field,
            Model model) {
        Reader reader = readerService.thisReader();
        List<String> sortFields = Arrays.asList("Название", "Автор", "Депозит", "Стоимость аренды");
        switch (field) {
            case "Название":
                field = "title";
                break;
            case "Автор":
                field = "author";
                break;
            case "Депозит":
                field = "depositAmount";
                break;
            case "Стоимость аренды":
                field = "rentalCost";
            default:
                break;
        }
        
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("books", bookService.getNotZeroSortedBooks(field));
        model.addAttribute("username", reader.getFirstName() + " " + reader.getLastName());
        return "goods_list";
    }

    @GetMapping({ "/add_to_cart/{id}", "/add_to_cart/{id}/" })
    public String addToCart(@PathVariable int id) {
        Reader reader = readerService.thisReader();

        CartElement cartElement = new CartElement(0, reader, bookService.findByIDBook(id));
        cartElementService.saveCartElement(cartElement);

        return "redirect:/home?field=" ;
    }

}