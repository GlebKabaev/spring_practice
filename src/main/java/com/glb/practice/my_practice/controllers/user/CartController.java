package com.glb.practice.my_practice.controllers.user;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.srevice.cart.CartElementServiceImpl;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.rental.RentalService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({ "/cart/", "/cart" })
@AllArgsConstructor
public class CartController {
    CartElementServiceImpl cartElementService;
    ReaderService readerService;
    RentalService rentalService;

    @GetMapping({ "", "/" })
    public String getMethodName(Model model) {
        Reader reader = readerService.thisReader();
        List<CartElement> cartElements = cartElementService.getCartElementsByReaderId(reader.getId());
        model.addAttribute("cart_elements", cartElements);
        return "reader_cart";
    }

    @PostMapping({ "/delete_element/{id}", "/delete_element/{id}/" })
    public String deleteBook(Model model, @PathVariable int id) {
        CartElement cartElement = cartElementService.findByIDCartElement(id);
        Reader reader = readerService.thisReader();
        if (reader.getId().equals(cartElement.getReader().getId())) {
            cartElementService.deleteCartElement(id);
        }
        return "redirect:/cart";
    }
//TODO добавить возможность выбрать адресс для заказа
//TODO изучить CSRF
    @PostMapping("/order")
    public String order(@RequestParam("expectedReturnDate") String expectedReturnDate) {
        Reader reader = readerService.thisReader();
        List<CartElement> cartElements = cartElementService.getCartElementsByReaderId(reader.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedReturn = null;
        Date today = new Date();
        try {
            expectedReturn = sdf.parse(expectedReturnDate);
        } catch (Exception e) {
            e.printStackTrace();

            return "redirect:/cart";
        }
        if(today.getTime() > expectedReturn.getTime()){
            
            return "redirect:/cart";
        }
        if(!cartElements.isEmpty()){
            for (CartElement cartElement : cartElements) {
                Book book = cartElement.getBook();
                Rental rental = new Rental();
                rental.setBook(book);
                rental.setReader(reader);
                rental.setExpectedReturnDate(expectedReturn);
                rental.setIssueDate(today);
                rentalService.saveRental(rental);
                cartElementService.deleteCartElement(cartElement.getId());
            }
        }else{
            return "redirect:/cart";
        }
        
        return "order";
    }

    

}
