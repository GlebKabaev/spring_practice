package com.glb.practice.my_practice.srevice.Impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.repository.BookRepository;
import com.glb.practice.my_practice.srevice.BookService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
@Primary
public class BookSeviceImpl implements BookService{
    private final BookRepository BOOK_REPOSITORIY;

    @Override
    public List<Book> getBooks() {
        return BOOK_REPOSITORIY.findAll();
    }

    @Override
    public Book saveBook(Book book) {
        return BOOK_REPOSITORIY.save(book);
    }

    @Override
    public Book findByIDBook(int id) {
        return BOOK_REPOSITORIY.findById(id).get();
    }

    @Override
    public Book updateBook(Book book) {
        return BOOK_REPOSITORIY.save(book);
    }

    @Override
    public void deleteBook(int id) {
        BOOK_REPOSITORIY.deleteById(id);
    }
    
}
