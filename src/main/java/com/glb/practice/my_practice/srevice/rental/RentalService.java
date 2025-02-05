package com.glb.practice.my_practice.srevice.rental;

import java.util.List;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.Rental;

public interface RentalService {
    public List<Rental> findAll();
    Rental save(Rental rental);
    Rental findById(int id);
    Rental update(Rental rental);
    void deleteById(int id);
    public List<Rental> findByReader(Reader reader);
} 
