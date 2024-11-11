package com.glb.practice.my_practice.srevice.rental;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.repository.rental.RentalRepository;
import com.glb.practice.my_practice.srevice.book.BookService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
@Primary
public class RentalServiceImpl implements RentalService {
    RentalRepository rentalRepository;
    BookService bookService;
    public List<Rental> getRentals() {
        return rentalRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    @Override
    public Rental saveRental(Rental rental) {
        Book book = bookService.findByIDBook(rental.getBook().getId());
        if(rental.getIssueDate().after(rental.getExpectedReturnDate())){
            throw new IllegalArgumentException("дата выдачи не может быть позже даты возврата");
        }
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);  // Уменьшаем количество на 1
            bookService.saveBook(book);  // Сохраняем обновленное количество книги
            return rentalRepository.save(rental);   // Сохраняем запись аренды
        }else{
            throw new IllegalArgumentException("книги нет в наличии");
        }
        
    }

    @Override
    public Rental findByIDRental(int id) {
        return rentalRepository.findById(id).get();
    }

    @Override
    public Rental updateRental(Rental rental) {
        if(rental.getIssueDate().after(rental.getExpectedReturnDate())){
            throw new IllegalArgumentException("дата выдачи не может быть позже даты возврата");
        }
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(int id) {
       rentalRepository.deleteById(id);
    }
    
}
