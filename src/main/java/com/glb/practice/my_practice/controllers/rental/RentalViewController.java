package com.glb.practice.my_practice.controllers.rental;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.rental.RentalService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin/rentals")
@AllArgsConstructor
public class RentalViewController {
    private final RentalService rentalService;
    private final ReaderService readerService;
    private final BookService bookService;

    @GetMapping({ "/", "" })
    public String showRentals(Model model) {

        model.addAttribute("rentals", rentalService.findAll());
        return "rental_list";
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public String showRentalData(Model model, @PathVariable int id) {
        model.addAttribute("rental", rentalService.findById(id));
        return "rental";
    }

    @GetMapping({ "/new", "/new/" })
    public String showCreateRentalForm(Model model) {
        model.addAttribute("readers", readerService.findAll("id"));
        model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
        model.addAttribute("rental", new Rental());
        return "rental_add-edit";
    }

    @PostMapping({ "/save_rental", "/save_rental/" })
    public String saveRental(@ModelAttribute("rental") Rental rental, Model model) {
        try {
            rentalService.save(rental);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("readers", readerService.findAll("id"));
            model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
            model.addAttribute("rental", new Rental());
            model.addAttribute("error", e.getMessage());
            return "rental_add-edit";
        }

        return "redirect:/admin/rentals";
    }

    @GetMapping({ "/delete/{id}", "/delete/{id}/" })
    public String deleteRental(Model model, @PathVariable int id) {
        rentalService.deleteById(id);
        return "redirect:/admin/rentals";
    }

    @GetMapping({ "/edit/{id}", "/edit/{id}/" })
    public String editRental(@PathVariable int id, Model model) {
        model.addAttribute("readers", readerService.findAll("id"));
        model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
        model.addAttribute("rental", rentalService.findById(id));
        return "rental_add-edit";
    }

    @PostMapping({ "/update_rental", "/update_rental/" })
    public String updateRental(@ModelAttribute("rental") Rental rental, Model model) {
        try {
            rentalService.update(rental);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("readers", readerService.findAll("id"));
            model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
            model.addAttribute("rental", rental);
            model.addAttribute("error", e.getMessage());
            return "rental_add-edit";
        }
        return "redirect:/admin/rentals";
    }

}
