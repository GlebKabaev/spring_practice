package com.glb.practice.my_practice.srevice.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.repository.InMemoryBookDAO;
import com.glb.practice.my_practice.srevice.BookService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class InMemoryBookService implements BookService {
    
    private InMemoryBookDAO bookDAO;
    @Override
    public List<Book> getBooks() {
        return bookDAO.getBooks();
    }
    
    @Override
    public Book saveBook(Book book) {
        
        return bookDAO.saveBook(book);
    }

    @Override
    public Book findByTitleBook(String title) {
       return bookDAO.findByTitleBook(title);
    }

    @Override
    public Book updateBook(Book book) {
       return bookDAO.updateBook(book);
    }

    @Override
    public void deleteBook(String title) {
        bookDAO.deleteBook(title);
    }
    
}
