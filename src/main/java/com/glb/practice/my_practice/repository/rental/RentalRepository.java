package com.glb.practice.my_practice.repository.rental;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentalRepository extends JpaRepository<Rental,Integer> {
    List<Rental> findByReader(Reader reader);
    List<Rental> findByExpectedReturnDateBeforeAndReturnedFalse(Date currentDate);
    Page<Rental> findAll(Pageable pageable);
}
