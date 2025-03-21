package com.glb.practice.my_practice.controllers.user;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.service.cart.CartElementService;
import com.glb.practice.my_practice.service.order.OrderService;
import com.glb.practice.my_practice.service.reader.ReaderService;
import com.glb.practice.my_practice.service.rental.RentalService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({ "/cart/", "/cart" })
@AllArgsConstructor
public class CartController {
    CartElementService cartElementService;
    ReaderService readerService;
    RentalService rentalService;
    OrderService orderService;
    @GetMapping({ "", "/" })
    public String getMethodName(Model model) {
        Reader reader = readerService.thisReader();
        List<CartElement> cartElements = cartElementService.findByReaderId(reader.getId());
        model.addAttribute("cart_elements", cartElements);
        return "reader_cart";
    }

    @DeleteMapping({ "/delete_element/{id}", "/delete_element/{id}/" })
    public String deleteById(Model model, @PathVariable int id) {
        cartElementService.deleteIfOwner(id);
        return "redirect:/cart";
    }

    // TODO добавить возможность выбрать адресс для заказа
    // TODO изучить CSRF
    @PostMapping("/order")
    public String order(@RequestParam("expectedReturnDate") String expectedReturnDate, Model model) {
        Reader reader = readerService.thisReader();
        List<CartElement> cartElements = cartElementService.findByReaderId(reader.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedReturn = null;
        Date today = new Date();
        StringBuilder errorBooks = new StringBuilder();
        try {
            expectedReturn = sdf.parse(expectedReturnDate);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Неправильный формат даты");
            return "redirect:/cart";
        }
        if (today.getTime() > expectedReturn.getTime()) {

            return "redirect:/cart";
        }
        if (!cartElements.isEmpty()) {
           orderService.processOrder(reader,expectedReturn,cartElements,errorBooks);
        } else {
            return "redirect:/cart";
        }
        if (errorBooks.length() > 0) {
            String errorMessage = "Некоторые книги из вашего списка не были оформлены по причине их отсутствия, а именно: " 
                + errorBooks.substring(0, errorBooks.length() - 1) + ".";
            model.addAttribute("error", errorMessage);
        }
        return "order";
    }

}
