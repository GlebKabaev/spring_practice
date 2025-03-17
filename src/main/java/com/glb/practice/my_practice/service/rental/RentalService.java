package com.glb.practice.my_practice.service.rental;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glb.practice.my_practice.exception.RentalNotFoundException;
import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.Rental;
import com.glb.practice.my_practice.repository.rental.RentalRepository;
import com.glb.practice.my_practice.service.book.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class RentalService {
    RentalRepository rentalRepository;
    BookService bookService;

    @Transactional(readOnly = true)
    public List<Rental> findAll() {
        return rentalRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public Page<Rental> findPaginated(int page, int size, String field) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(field)));
        return rentalRepository.findAll(pageable);
    }

    @Transactional
    public Rental save(Rental rental) {
        Book book = bookService.findById(rental.getBook().getId());
        if (rental.getBook().isDeleted()) {
            throw new IllegalArgumentException("данная книга удалена");
        }
        if (rental.getIssueDate().after(rental.getExpectedReturnDate())) {
            throw new IllegalArgumentException("дата выдачи не может быть позже даты возврата");
        }
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookService.save(book);
            rental.setReturned(false);
            return rentalRepository.save(rental);
        } else {
            throw new IllegalArgumentException("книги нет в наличии");
        }

    }

    @Transactional(readOnly = true)
    public Rental findById(int id) {
        return rentalRepository.findById(id).get();
    }

    @Transactional
    public Rental update(Rental rental) {
        if (rental.getIssueDate().after(rental.getExpectedReturnDate())) {
            throw new IllegalArgumentException("дата выдачи не может быть позже даты возврата");
        }
        return rentalRepository.save(rental);
    }

    @Transactional
    public void deleteById(int id) {
        rentalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Rental> findByReader(int page, int size, String field,Reader reader) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(field)));
        return rentalRepository.findByReader(reader,pageable);
    }
    @Transactional(readOnly = true)
    public List<Rental> findByReader(Reader reader) {
        return rentalRepository.findByReader(reader);
    }

    @Transactional(readOnly = true)
    public Page<Rental> findExpiredByReaderPaginaited(int page, int size, String field, Reader reader) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(field)));
        return rentalRepository.findByExpectedReturnDateBeforeAndReturnedFalseAndReader(new Date(), reader, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Rental> findAllExpiredRentals(int page, int size) {
        Date currentDate = new Date();
        Pageable pageable = PageRequest.of(page, size);
        Page<Rental> expiredRentals = rentalRepository.findByExpectedReturnDateBeforeAndReturnedFalse(currentDate,
                pageable);
        return expiredRentals;
    }

    public Rental toggleRentalStatus(int id) {
        Rental rental = findById(id);
        if (rental == null) {
            throw new RentalNotFoundException("Rental with id " + id + " not found");
        }
        rental.setReturned(!rental.getReturned());
        return update(rental);
    }
}
