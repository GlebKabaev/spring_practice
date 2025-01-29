package com.glb.practice.my_practice.srevice.book;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.repository.book.BookRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class BookSeviceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true) 
    public List<Book> getBooks(String field) {
        return bookRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }

    @Override
    @Transactional(readOnly = true) 
    public List<Book> getNotZeroBooks() {
        return bookRepository.findByQuantityNot(0, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional 
    public Book saveBook(Book book) {
        if (book.getQuantity() >= 0 && book.getRentalCost() >= 0 && book.getDepositAmount() >= 0) {
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("поле не может быть отрицательным");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Book findByIDBook(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        if (book.getQuantity() >= 0 && book.getRentalCost() >= 0 && book.getDepositAmount() >= 0) {
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("поле не может быть отрицательным");
        }
    }

    @Override
    @Transactional 
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true) 
    public List<Book> getNotZeroSortedBooks(String field) {
        return bookRepository.findByQuantityNot(0, Sort.by(Sort.Direction.DESC, field));
    }
}