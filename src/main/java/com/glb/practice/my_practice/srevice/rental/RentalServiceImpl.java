package com.glb.practice.my_practice.srevice.rental;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.repository.rental.RentalRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
@Primary
public class RentalServiceImpl implements RentalService {
    RentalRepository rentalRepository;
    
    public List<Rental> getRentals() {
        return rentalRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    @Override
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public Rental findByIDRental(int id) {
        return rentalRepository.findById(id).get();
    }

    @Override
    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(int id) {
       rentalRepository.deleteById(id);
    }
    
}
