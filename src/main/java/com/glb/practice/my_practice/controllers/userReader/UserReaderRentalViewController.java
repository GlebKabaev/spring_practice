package com.glb.practice.my_practice.controllers.userReader;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.models.UserReader;
import com.glb.practice.my_practice.service.book.BookService;
import com.glb.practice.my_practice.service.rental.RentalService;
import com.glb.practice.my_practice.service.userReader.UserReaderService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin/users-readers/{userReaderID}/rentals")
@AllArgsConstructor

public class UserReaderRentalViewController {
    private final UserReaderService userReaderService;
    private final RentalService rentalService;
    private final BookService bookService;

    @GetMapping
    public String showReaderRentals(@PathVariable int userReaderID, Model model) {

        UserReader userReader = userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();
        List<Rental> rentals = rentalService.findByReader(reader);
        model.addAttribute("reader", reader);
        model.addAttribute("userReader", userReader);
        model.addAttribute("rentals", rentals);
        return "admin_reader_rentals";
    }

    @GetMapping("/new")
    public String showCreateRentalForm(@PathVariable int userReaderID, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        model.addAttribute("reader", userReader.getReader());
        model.addAttribute("userReader", userReader);
        model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
        model.addAttribute("rental", new Rental());
        return "user_reader_new_rental";
    }

    @PostMapping("/new")
    public String saveRental(@PathVariable int userReaderID, @ModelAttribute("rental") Rental rental, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();
        try {
            rental.setReader(reader);
            rentalService.save(rental);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("reader", reader);
            model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
            model.addAttribute("rental", new Rental());
            model.addAttribute("error", e.getMessage());
            return "user_reader_new_rental";
        }

        return "redirect:/admin/users-readers/%d/rentals".formatted(userReader.getId());
    }
}
