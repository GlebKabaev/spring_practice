package com.glb.practice.my_practice.srevice.rental;

import java.util.List;

import com.glb.practice.my_practice.models.Rental;

public interface RentalService {
    public List<Rental> getRentals();
    Rental saveRental(Rental rental);
    Rental findByIDRental(int id);
    Rental updateRental(Rental rental);
    void deleteRental(int id);
} 
