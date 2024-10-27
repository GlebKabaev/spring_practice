package com.glb.practice.my_practice.controllers;


import java.util.List;


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
    @GetMapping("/{id}")
    public Book findByIDBook(@PathVariable int id){
        return bookSrevice.findByIDBook(id);
    }
    @PutMapping("/update_book")
    public Book updateBook(@RequestBody Book book){
        return bookSrevice.updateBook(book);
    }
    @DeleteMapping("delete_book/{id}")
    public void deleteBook(@PathVariable int id){
        bookSrevice.deleteBook(id);
    }
    
}
