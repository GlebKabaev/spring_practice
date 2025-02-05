package com.glb.practice.my_practice.srevice.rental;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.Reader;
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

    @Transactional(readOnly = true)
    public List<Rental> findAll() {
        return rentalRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    @Override
    public Rental save(Rental rental) {
        Book book = bookService.findById(rental.getBook().getId());
        if (rental.getBook().isDeleted()) {
            throw new IllegalArgumentException("данная книга удалена");
        }
        if (rental.getIssueDate().after(rental.getExpectedReturnDate())) {
            throw new IllegalArgumentException("дата выдачи не может быть позже даты возврата");
        }
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1); // Уменьшаем количество на 1
            bookService.save(book); // Сохраняем обновленное количество книги
            return rentalRepository.save(rental); // Сохраняем запись аренды
        } else {
            throw new IllegalArgumentException("книги нет в наличии");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Rental findById(int id) {
        return rentalRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Rental update(Rental rental) {
        if (rental.getIssueDate().after(rental.getExpectedReturnDate())) {
            throw new IllegalArgumentException("дата выдачи не может быть позже даты возврата");
        }
        return rentalRepository.save(rental);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        rentalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Rental> findByReader(Reader reader) {
        return rentalRepository.findByReader(reader);
    }

}
