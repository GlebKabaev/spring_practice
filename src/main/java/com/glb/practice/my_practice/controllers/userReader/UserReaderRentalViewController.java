package com.glb.practice.my_practice.controllers.userReader;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/users-readers/{userReaderID}/rentals")
@AllArgsConstructor

public class UserReaderRentalViewController {
    private final UserReaderService userReaderService;
    private final RentalService rentalService;
    private final BookService bookService;

    @GetMapping
    public String showReaderRentals(Model model, @PathVariable int userReaderID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "field", defaultValue = "issueDate") String field) {

        UserReader userReader = userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();
        Page<Rental> rentalPage = rentalService.findByReader(page, size, field, reader);

        model.addAttribute("reader", reader);
        model.addAttribute("userReader", userReader);
        model.addAttribute("rentals", rentalPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rentalPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("field", field);
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
            model.addAttribute("error", e.getMessage());
            return showCreateRentalForm(userReaderID,model);
        }

        return "redirect:/admin/users-readers/%d/rentals".formatted(userReader.getId());
    }

    @PatchMapping("/toggleRentalStatus/{rentalID}")
    public String toggleRentalStatus(@PathVariable int userReaderID, @PathVariable int rentalID) {
        rentalService.toggleRentalStatus(rentalID);

        return "redirect:/admin/users-readers/%d/rentals".formatted(userReaderID);
    }
    @PatchMapping("/toggleReceivedStatus/{rentalID}")
    public String toggleReceivedStatus(@PathVariable int userReaderID, @PathVariable int rentalID) {
        rentalService.toggleReceivedStatus(rentalID);

        return "redirect:/admin/users-readers/%d/rentals".formatted(userReaderID);
    }

    @GetMapping("/expired")
    public String getExpiredRentals(Model model, @PathVariable int userReaderID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "field", defaultValue = "issueDate") String field) {
        UserReader userReader= userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();
        Page<Rental> rentalPage = rentalService.findExpiredByReaderPaginaited(page, size,field,reader);
        model.addAttribute("reader", reader);
        model.addAttribute("userReader", userReader);
        model.addAttribute("rentals", rentalPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rentalPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("field", field);
        model.addAttribute("expired", true);
        return "admin_reader_rentals";
    }

}
