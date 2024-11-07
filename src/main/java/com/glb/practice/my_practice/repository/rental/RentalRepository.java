package com.glb.practice.my_practice.repository.rental;

import org.springframework.data.jpa.repository.JpaRepository;


import com.glb.practice.my_practice.models.Rental;

public interface RentalRepository extends JpaRepository<Rental,Integer> {
    
}
