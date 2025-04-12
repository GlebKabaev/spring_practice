package com.glb.practice.my_practice.repository.rental;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentalRepository extends JpaRepository<Rental,Integer> {
    Page<Rental> findByExpectedReturnDateBeforeAndReturnedFalseAndReader(LocalDate date, Reader reader, Pageable pageable);
    Page<Rental> findByReader(Reader reader, Pageable pageable);
    List<Rental> findByReader(Reader reader);
    Page<Rental> findByExpectedReturnDateBeforeAndReturnedFalse(LocalDate date, Pageable pageable);
    Page<Rental> findAll(Pageable pageable);
}
