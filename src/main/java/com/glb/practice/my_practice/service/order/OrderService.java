package com.glb.practice.my_practice.service.order;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.service.cart.CartElementService;
import com.glb.practice.my_practice.service.rental.RentalService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    RentalService rentalService;
    CartElementService cartElementService;
    public void processOrder(Reader reader, Date expectedReturnDate,Date orderDate, List<CartElement> cartElements,
            StringBuilder errorBooks) {
        for (CartElement cartElement : cartElements) {
            Book book = cartElement.getBook();
            Rental rental = new Rental(0, reader, book, orderDate, expectedReturnDate, false);
            try {
                rentalService.save(rental);
            } catch (Exception e) {
                errorBooks.append(" ").append(book.getTitle()).append(",");
            }
            cartElementService.deleteById(cartElement.getId());
        }
    }
}
