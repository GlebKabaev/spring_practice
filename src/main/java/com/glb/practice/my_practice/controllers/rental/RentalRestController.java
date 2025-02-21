package com.glb.practice.my_practice.controllers.rental;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.service.rental.RentalService;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalRestController {
    private final RentalService rentalService;

    @GetMapping({"/",""})
    public List<Rental> findAll() {
        return rentalService.findAll();
    }
    @PostMapping({"/save_rental","/save_rental/"})
    public Rental save(@RequestBody Rental rental) {
        return rentalService.save(rental);
    }
    @GetMapping({"/{id}","/{id}/"})
    public Rental findById(@PathVariable int id) {
        return rentalService.findById(id);
    }
    @PutMapping({"/update_rental", "/update_rental/"})
    public Rental update(@RequestBody Rental rental) {
        return rentalService.update(rental);
    }
    @DeleteMapping({"/delete_rental/{id}","/delete_rental/{id}/"})
    public void deleteById(@PathVariable int id) {
        rentalService.deleteById(id);
    }
}
