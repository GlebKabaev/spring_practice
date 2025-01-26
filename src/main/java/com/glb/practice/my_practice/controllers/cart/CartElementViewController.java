package com.glb.practice.my_practice.controllers.cart;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.cart.CartElementService;

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
        model.addAttribute("carts", cartService.getCartElements(field));
        return "cart_list";
    }

    @GetMapping({"/new", "/new/"})
    public String showCreateCartElementForm(Model model) {
        model.addAttribute("readers", readerService.getReaders("id"));
        model.addAttribute("books", bookService.getBooks("id"));
        model.addAttribute("cart", new CartElement());
        return "cart_add-edit";
    }

    @PostMapping({"/save_cart", "/save_cart/"})
    public String saveCartElement(@ModelAttribute("cart") CartElement cart, Model model) {
        try {
            cartService.saveCartElement(cart);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("readers", readerService.getReaders("id"));
            model.addAttribute("books", bookService.getBooks("id"));
            model.addAttribute("cart", cart);
            model.addAttribute("error", e.getMessage());
            return "cart_add-edit";
        }
        return "redirect:/admin/carts";
    }

    @GetMapping({"/delete/{id}", "/delete/{id}/"})
    public String deleteCartElement(@PathVariable int id) {
        cartService.deleteCartElement(id);
        return "redirect:/admin/carts";
    }

    @GetMapping({"/edit/{id}", "/edit/{id}/"})
    public String editCartElement(@PathVariable int id, Model model) {
        model.addAttribute("readers", readerService.getReaders("id"));
        model.addAttribute("books", bookService.getBooks("id"));
        model.addAttribute("cart", cartService.findByIDCartElement(id));
        return "cart_add-edit";
    }

    @PostMapping({"/update_cart", "/update_cart/"})
    public String updateCartElement(@ModelAttribute("cart") CartElement cart, Model model) {
        try {
            cartService.updateCartElement(cart);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("readers", readerService.getReaders("id"));
            model.addAttribute("books", bookService.getBooks("id"));
            model.addAttribute("cart", cart);
            model.addAttribute("error", e.getMessage());
            return "cart_add-edit";
        }
        return "redirect:/admin/carts";
    }
}
