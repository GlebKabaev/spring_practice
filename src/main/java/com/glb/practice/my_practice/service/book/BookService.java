package com.glb.practice.my_practice.service.book;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.repository.book.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> findAll(String field) {
        return bookRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }

    @Transactional(readOnly = true)
    public Page<Book> findPaginated(int page, int size, String field) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(field)));
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> findPaginatedSearched(int page, int size, String field, String searchQuery) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(field)));
        if (searchQuery != null && !searchQuery.isEmpty()) {
            return bookRepository.findByQuantityNotAndDeletedFalseAndTitleContainingIgnoreCase(0,searchQuery, pageable);
        } else {
            return bookRepository.findByQuantityNotAndDeletedFalse(0,pageable);
        }
    }

    @Transactional(readOnly = true)
    public List<Book> findByQuantityNotZeroAndDeletedFalse() {
        return bookRepository.findByQuantityNotAndDeletedFalse(0, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Book save(Book book) {
        if (book.getQuantity() >= 0 && book.getRentalCost() >= 0 && book.getDepositAmount() >= 0) {
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("поле не может быть отрицательным");
        }
    }

    @Transactional(readOnly = true)
    public Book findById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Transactional
    public Book update(Book book) {
        if (book.getQuantity() >= 0 && book.getRentalCost() >= 0 && book.getDepositAmount() >= 0) {
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("поле не может быть отрицательным");
        }
    }

    @Transactional
    public void deleteById(int id) {
        bookRepository.findById(id).get().setDeleted(true);
    }

    @Transactional(readOnly = true)
    public List<Book> getNotZeroSortedBooks(String field) {
        return bookRepository.findByQuantityNotAndDeletedFalse(0, Sort.by(Sort.Direction.DESC, field));
    }
}