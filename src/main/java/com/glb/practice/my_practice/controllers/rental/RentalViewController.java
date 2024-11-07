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
import com.glb.practice.my_practice.srevice.rental.RentalService;
import com.glb.practice.my_practice.srevice.user.UserService;

import lombok.AllArgsConstructor;
import lombok.Delegate;
@Controller
@RequestMapping("/rentals")
@AllArgsConstructor
public class RentalViewController {
    private final RentalService rentalService;
    private final UserService userService;
    private final BookService bookService;
    @GetMapping
    public String showRentals(Model model) {
        model.addAttribute("rentals", rentalService.getRentals());
    return "rental_list";
    }
    @GetMapping("/{id}")
    public String showRentalData(Model model, @PathVariable int id) {
        model.addAttribute("rental", rentalService.findByIDRental(id));
        return "rental";
    }
    @GetMapping("/new")
    public String showCreateRentalForm(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("rental", new Rental()); 
    return "rental_add-edit"; 
    }
    @PostMapping("/save_rental")
    public String saveRental(@ModelAttribute("rental") Rental rental) {
        rentalService.saveRental(rental); 

        return "redirect:/rentals"; 
    }
    @GetMapping("/delete/{id}")
    public String deleteRental(Model model,@PathVariable int id) {
        rentalService.deleteRental(id);
        return "redirect:/rentals";
    }
    @GetMapping("/edit/{id}")
    public String editRental(@PathVariable int id, Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("rental", rentalService.findByIDRental(id));
        return "rental_add-edit";
    }
    @PostMapping("/update_rental")
    public String updateRental(@ModelAttribute("rental") Rental rental) {
    
    if (rental.getId() != null && rentalService.findByIDRental(rental.getId()) != null) {
        
        rentalService.updateRental(rental);
    } else {
       
        throw new IllegalArgumentException("Аренда с таким ID не найдена или ID не указан");
    }
        return "redirect:/rentals";
    }

}
