package com.glb.practice.my_practice.controllers.book;


import java.util.List;


import org.springframework.web.bind.annotation.*;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.service.book.BookService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookRestController {
    private final BookService bookService;
    @GetMapping({"/",""})
    public List<Book> findAll(){
        
        return bookService.findAll("id");
    }
    @PostMapping({"/save_book","/save_book/"})
    public Book save(@RequestBody Book book){
        return bookService.save(book);
    }
    @GetMapping({"/{id}","/{id}/"})
    public Book findById(@PathVariable int id){
        return bookService.findById(id);
    }
    @PutMapping({"/update_book","/update_book/"})
    public Book update(@RequestBody Book book){
        return bookService.update(book);
    }
    @DeleteMapping({"delete_book/{id}","delete_book/{id}/"})
    public void deleteById(@PathVariable int id){
        bookService.deleteById(id);
    }
    
}
