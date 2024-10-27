package com.glb.practice.my_practice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Repository;

import com.glb.practice.my_practice.models.Book;
@Repository
public class InMemoryBookDAO {
    private final List<Book> BOOKS=new ArrayList<>();
    
    public List<Book> getBooks() {
        return BOOKS;
    }
    public Book saveBook(Book book) {
        BOOKS.add(book);
        return book;
    }

    
    public Book findByIDBook(int id) {
       return BOOKS.stream()
               .filter(element -> element.getId()==id).findFirst().orElse(null);
    }

    
    public Book updateBook(Book book) {
        var bookIndex=IntStream.range(0, BOOKS.size())
                                .filter(element->BOOKS
                                        .get(element).getTitle()
                                        .equalsIgnoreCase(book.getTitle()))
                                .findFirst()
                                .orElse(-1);
        if(bookIndex >-1){
            BOOKS.set(bookIndex, book);
            return book;
        }
        return null;
    }

    
    public void deleteBook(int id) {
       var book= findByIDBook(id);
       if(book!=null){
           BOOKS.remove(book);
       }
    }
}