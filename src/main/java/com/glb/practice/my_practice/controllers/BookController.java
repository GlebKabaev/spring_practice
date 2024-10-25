package com.glb.practice.my_practice.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.srevice.BookService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookSrevice;
    @GetMapping
    public List<Book> getBooks(){
        
        return bookSrevice.getBooks();
    }
    @PostMapping("/save_book")
    public Book saveBook(@RequestBody Book book){
        return bookSrevice.saveBook(book);
    }
    @GetMapping("/{title}")
    public Book findByTitleBook(@PathVariable String title){
        return bookSrevice.findByTitleBook(title);
    }
    @PutMapping("/update_book")
    public Book updateBook(@RequestBody Book book){
        return bookSrevice.updateBook(book);
    }
    @DeleteMapping("delete_book/{title}")
    public void deleteBook(@PathVariable String title){
        bookSrevice.deleteBook(title);
    }
    
}
