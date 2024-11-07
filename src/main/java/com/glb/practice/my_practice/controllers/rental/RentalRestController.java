package com.glb.practice.my_practice.controllers.rental;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.srevice.rental.RentalService;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalRestController {
    private final RentalService rentalService;

    @GetMapping
    public List<Rental> getRentals() {
        return rentalService.getRentals();
    }
    @PostMapping("/save_rental")
    public Rental saveRental(@RequestBody Rental rental) {
        return rentalService.saveRental(rental);
    }
    @GetMapping("/{id}")
    public Rental findByIDRental(@PathVariable int id) {
        return rentalService.findByIDRental(id);
    }
    @PutMapping("/update_rental")
    public Rental updateRental(@RequestBody Rental rental) {
        return rentalService.updateRental(rental);
    }
    @DeleteMapping("/delete_rental/{id}")
    public void deleteRental(@PathVariable int id) {
        rentalService.deleteRental(id);
    }
}
