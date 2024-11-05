package com.glb.practice.my_practice.controllers.book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.srevice.book.BookService;

import lombok.AllArgsConstructor;
@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookViewController {
    private final BookService bookService;

    @GetMapping
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("title", "Список книг");
    return "list";
    }
    @GetMapping("/{id}")
    public String showBookData(Model model, @PathVariable int id) {
        model.addAttribute("book", bookService.findByIDBook(id));
        return "book";
    }
    @GetMapping("/delete_book/{id}")
    public String deleteBook(Model model,@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    @GetMapping("/new")
    public String showCreateBookForm(Model model) {
        model.addAttribute("book", new Book()); // добавляем пустой объект Book для привязки формы
    return "add-edit"; 
    }
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.findByIDBook(id));
        return "add-edit";
    }
    @PostMapping("/save_book")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book); // создаем новую книгу

        return "redirect:/books"; // перенаправляем на список книг // перенаправляем на список книг
    }
    @PostMapping("/update_book")
    public String updateBook(@ModelAttribute("book") Book book) {
    // Проверяем, существует ли книга с таким ID
    if (book.getId() != null && bookService.findByIDBook(book.getId()) != null) {
        // Обновляем книгу
        // Код для обновления книги в базе данных или списке
        bookService.updateBook(book);
    } else {
        // Если ID отсутствует или книга не найдена, выбрасываем исключение или сохраняем новую книгу
        throw new IllegalArgumentException("Книга с таким ID не найдена или ID не указан");
    }
        return "redirect:/books";
    }

    

}
