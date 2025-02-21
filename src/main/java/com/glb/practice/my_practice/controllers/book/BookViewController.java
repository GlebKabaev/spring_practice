package com.glb.practice.my_practice.controllers.book;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.Image;
import com.glb.practice.my_practice.service.book.BookService;
import com.glb.practice.my_practice.service.image.ImageService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin/books")
@AllArgsConstructor
public class BookViewController {
    private final BookService bookService;
    private final ImageService imageService;

    @GetMapping({ "/", "" })
    public String showBooks(Model model) {
        List<String> sortFields = Arrays.asList("id", "Название", "Автор");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("books", bookService.findAll("id"));
        return "book_list";
    }

    @GetMapping("/sort")
    public String sortBooks(@RequestParam("field") String field, Model model) {
        List<String> sortFields = Arrays.asList("id", "Название", "Автор");
        switch (field) {
            case "Название":
                field = "title";
                break;
            case "Автор":
                field = "author";
                break;
            default:
                break;
        }
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("books", bookService.findAll(field));
        return "book_list";

    }

    @GetMapping({ "/{id}", "/{id}/" })
    public String showBookData(Model model, @PathVariable int id) {
        Book book = bookService.findById(id);
        if (book.getImage() != null) {
            String base64Image = imageService.getImageBase64(book.getImage());
            model.addAttribute("base64Image", base64Image);
        } else {
            model.addAttribute("base64Image", null);
        }
        model.addAttribute("book", book);
        return "book";
    }

    @PatchMapping({ "delete_book/{id}", "delete_book/{id}/" })
    public String deleteById(Model model, @PathVariable int id) {
        bookService.deleteById(id);
        return "redirect:/admin/books";
    }

    @GetMapping({ "/new", "/new/" })
    public String showCreateBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book_add-edit";
    }

    @GetMapping({ "/edit/{id}", "/edit/{id}/" })
    public String editBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book_add-edit";
    }

    @PostMapping({ "/save_book", "/save_book/" })
    public String save(@ModelAttribute("book") Book book, @RequestParam("file") MultipartFile file, Model model) {
        try {
            if (file != null && !file.isEmpty()) {
                Image image = imageService.save(file);
                book.setImage(image);
            }
            bookService.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("book", book);
            model.addAttribute("error", e.getMessage());
            return "book_add-edit";
        }
        return "redirect:/admin/books";
    }

    @PatchMapping({ "/update_book", "/update_book/" })
    public String update(@ModelAttribute("book") Book book, @RequestParam("file") MultipartFile file, Model model) {
        try {
            if (file != null && !file.isEmpty()) {
                Image image = imageService.save(file);
                book.setImage(image);
            }
            bookService.update(book);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("book", book);
            model.addAttribute("error", e.getMessage());
            return "book_add-edit";
        }
        return "redirect:/admin/books";
    }

}
