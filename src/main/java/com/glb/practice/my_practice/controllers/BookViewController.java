package com.glb.practice.my_practice.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.srevice.BookService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookViewController {
    private final BookService bookService;

    @GetMapping
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "list";
    }
    @GetMapping("/{id}")
    public String showBookData(Model model, @PathVariable int id) {
        model.addAttribute("book", bookService.findByIDBook(id));
        return "book";
    }
    @GetMapping("/delete_book/{id}")
    public String getMethodName(Model model,@PathVariable int id) {
        //model.addAttribute("book", bookService.findByIDBook(id));
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    
    

}
