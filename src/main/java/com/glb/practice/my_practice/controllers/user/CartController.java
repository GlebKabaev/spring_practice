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
    public String cart(Model model) {
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
    public String order(@RequestParam("expectedReturnDate") String expectedReturnDate, Model model,
    @RequestParam("orderDate") String orderDate) {
        Reader reader = readerService.thisReader();
        List<CartElement> cartElements = cartElementService.findByReaderId(reader.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedReturnSDF = null;
        Date orderDateSDF = null;
        Date today = new Date();
        StringBuilder errorBooks = new StringBuilder();
        try {
            expectedReturnSDF = sdf.parse(expectedReturnDate);
            orderDateSDF=sdf.parse(orderDate);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Неправильный формат даты");
            return cart(model);
        }
        if (orderDateSDF.getTime() > expectedReturnSDF.getTime()) {
            model.addAttribute("error", "Дата аренды не может быть позже даты возврата.");
            return cart(model);
        }
        if (today.getTime() > orderDateSDF.getTime()) {
            model.addAttribute("error", "Дата аренды не может быть позже сегодняшней даты.");
            return cart(model);
        }
        if (!cartElements.isEmpty()) {
           orderService.processOrder(reader,expectedReturnSDF,orderDateSDF,cartElements,errorBooks);
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
