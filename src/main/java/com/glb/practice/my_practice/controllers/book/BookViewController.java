package com.glb.practice.my_practice.controllers.book;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.srevice.book.BookService;

import lombok.AllArgsConstructor;
@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookViewController {
    private final BookService bookService;

    @GetMapping({"/",""})
    public String showBooks(Model model) {
        //TODO добавить фильтры
        List<String> sortFields = Arrays.asList("id", "title", "author");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("books", bookService.getBooks("id"));
    return "book_list";
    }
    @GetMapping("/books/sort")
    public String sortBooks(@RequestParam("field") String field, Model model) {
        model.addAttribute("books", bookService.getBooks(field));
        return "book_list";
        //TODO Добить сортировку
    }
    @GetMapping({"/{id}","/{id}/"})
    public String showBookData(Model model, @PathVariable int id) {
        //TODO добавить изображение и описание
        model.addAttribute("book", bookService.findByIDBook(id));
        return "book";
    }
    @GetMapping({"delete_book/{id}","delete_book/{id}/"})
    public String deleteBook(Model model,@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    @GetMapping({"/new","/new/"})
    public String showCreateBookForm(Model model) {
        model.addAttribute("book", new Book()); 
    return "book_add-edit"; 
    }
    @GetMapping({"/edit/{id}","/edit/{id}/"})
    public String editBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.findByIDBook(id));
        return "book_add-edit";
    }
    @PostMapping({"/save_book", "/save_book/"})
    public String saveBook(@ModelAttribute("book") Book book,Model model) {
        try{
            bookService.saveBook(book); 
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("book", book);
            model.addAttribute("error", e.getMessage());
            return "book_add-edit"; 
        }
        return "redirect:/books"; 
    }
    @PostMapping({"/update_book","/update_book/"})
    public String updateBook(@ModelAttribute("book") Book book, Model model) {
        try{
            bookService.updateBook(book);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("book", book);
            model.addAttribute("error", e.getMessage());
            return "book_add-edit"; 
        }
        return "redirect:/books";
    }

    

}
