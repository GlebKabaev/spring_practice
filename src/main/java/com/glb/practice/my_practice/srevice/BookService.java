package com.glb.practice.my_practice.srevice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Book;


public interface BookService {
    public List<Book> getBooks();
    Book saveBook(Book book);
    Book findByTitleBook(String title);
    Book updateBook(Book book);
    void deleteBook(String title);
}
