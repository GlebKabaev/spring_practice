package com.glb.practice.my_practice.controllers.book;


import java.util.List;


import org.springframework.web.bind.annotation.*;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.srevice.book.BookService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookRestController {
    private final BookService bookService;
    @GetMapping({"/",""})
    public List<Book> getBooks(){
        
        return bookService.getBooks("id");
    }
    @PostMapping({"/save_book","/save_book/"})
    public Book saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }
    @GetMapping({"/{id}","/{id}/"})
    public Book findByIDBook(@PathVariable int id){
        return bookService.findByIDBook(id);
    }
    @PutMapping({"/update_book","/update_book/"})
    public Book updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }
    @DeleteMapping({"delete_book/{id}","delete_book/{id}/"})
    public void deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
    }
    
}
