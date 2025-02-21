package com.glb.practice.my_practice.controllers.cart;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.service.book.BookService;
import com.glb.practice.my_practice.service.cart.CartElementService;
import com.glb.practice.my_practice.service.reader.ReaderService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin/carts")
@AllArgsConstructor
public class CartElementViewController {

    private final CartElementService cartService;
    private final ReaderService readerService;
    private final BookService bookService;

    @GetMapping({"/", ""})
    public String showCartElements(@RequestParam (defaultValue = "id", required = false) String field, Model model) {
        List<String> sortFields = Arrays.asList("id","reader.lastName");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("carts", cartService.findAll(field));
        return "cart_list";
    }

    @GetMapping({"/new", "/new/"})
    public String showCreateCartElementForm(Model model) {
        model.addAttribute("readers", readerService.findAll("id"));
        model.addAttribute("books", bookService.findAll("id"));
        model.addAttribute("cart", new CartElement());
        return "cart_add-edit";
    }

    @PostMapping({"/save_cart", "/save_cart/"})
    public String save(@ModelAttribute("cart") CartElement cart, Model model) {
        try {
            cartService.save(cart);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("readers", readerService.findAll("id"));
            model.addAttribute("books", bookService.findAll("id"));
            model.addAttribute("cart", cart);
            model.addAttribute("error", e.getMessage());
            return "cart_add-edit";
        }
        return "redirect:/admin/carts";
    }

    @DeleteMapping({"/delete/{id}", "/delete/{id}/"})
    public String deleteById(@PathVariable int id) {
        cartService.deleteById(id);
        return "redirect:/admin/carts";
    }

    @GetMapping({"/edit/{id}", "/edit/{id}/"})
    public String editCartElement(@PathVariable int id, Model model) {
        model.addAttribute("readers", readerService.findAll("id"));
        model.addAttribute("books", bookService.findAll("id"));
        model.addAttribute("cart", cartService.findById(id));
        return "cart_add-edit";
    }

    @PatchMapping({"/update_cart", "/update_cart/"})
    public String update(@ModelAttribute("cart") CartElement cart, Model model) {
        try {
            cartService.update(cart);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("readers", readerService.findAll("id"));
            model.addAttribute("books", bookService.findAll("id"));
            model.addAttribute("cart", cart);
            model.addAttribute("error", e.getMessage());
            return "cart_add-edit";
        }
        return "redirect:/admin/carts";
    }
}
