package com.glb.practice.my_practice.controllers.userReader;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.UserReader;
import com.glb.practice.my_practice.service.book.BookService;
import com.glb.practice.my_practice.service.cart.CartElementService;
import com.glb.practice.my_practice.service.userReader.UserReaderService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin/users-readers/{userReaderID}/cart")
@AllArgsConstructor
public class UserReaderCartViewController {
    UserReaderService userReaderService;
    CartElementService cartElementService;
    BookService bookService;

    @GetMapping
    public String showReaderCart(@PathVariable int userReaderID, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();
        List<CartElement> cartElements = cartElementService.findByReaderId(reader.getId());

        model.addAttribute("userReader", userReader);
        model.addAttribute("cart_elements", cartElements);
        return "admin_userReader_cart";
    }

    @GetMapping("/new")
    public String newBookToReaderCart(@PathVariable int userReaderID, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();

        List<Book> books = bookService.findByQuantityNotZeroAndDeletedFalse();
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);
        return "admin_userReader_add_to_cart_book";
    }

    /** Добавление книги в корзину читателя */
    @PostMapping("/new")
    public String addBookToReaderCart(@PathVariable int userReaderID, @RequestParam("bookId") int bookId) {
        UserReader userReader = userReaderService.findById(userReaderID);

        Reader reader = userReader.getReader();
        Book book = bookService.findById(bookId);
        cartElementService.save(new CartElement(0, reader, book));

        return "redirect:/admin/users-readers/%d/cart".formatted(userReaderID);
    }
    
    @DeleteMapping("delete/{cartID}")
    public String deleteBookFromCart(@PathVariable int userReaderID,@PathVariable int cartID){
        cartElementService.deleteById(cartID);
        return "redirect:/admin/users-readers/%d/cart".formatted(userReaderID);

    }
}
