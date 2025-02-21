package com.glb.practice.my_practice.controllers.reader;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.service.book.BookService;
import com.glb.practice.my_practice.service.cart.CartElementService;
import com.glb.practice.my_practice.service.reader.ReaderService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin/readers")
@AllArgsConstructor
public class ReaderViewController {
    private final ReaderService readerService;
    private final CartElementService cartElementService;
    private final BookService bookService;

    @GetMapping({ "/", "" })
    public String showReaders(Model model) {

        List<String> sortFields = Arrays.asList("id", "Фамилия", "Имя", "Отчество");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("readers", readerService.findAll("id"));
        return "reader_list";
    }

    @GetMapping("/sort")
    public String sortReaders(@RequestParam("field") String field, Model model) {

        List<String> sortFields = Arrays.asList("id", "Фамилия", "Имя", "Отчество");
        switch (field) {
            case "Фамилия":
                field = "lastName";
                break;
            case "Имя":
                field = "firstName";
                break;
            case "Отчество":
                field = "middleName";
                break;
            default:
                break;
        }
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("readers", readerService.findAll(field));
        return "reader_list";
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public String showReaderData(Model model, @PathVariable int id) {
        // TODO список арендованных книг пользователем и время захода на сайт
        try {
            Reader reader = readerService.findByIDReader(id);
            model.addAttribute("reader", reader);
            return "reader";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found");
        }

    }

    @GetMapping("/{id}/cart")
    public String showReaderCart(Model model, @PathVariable int id) {
        try {
            Reader reader = readerService.findByIDReader(id);
            model.addAttribute("reader", reader);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found");
        }
        List<CartElement> cartElements = cartElementService.findByReaderId(id);
        model.addAttribute("cart_elements", cartElements);
        return "admin_reader_cart";
    }

    @GetMapping({ "/new", "/new/" })
    public String showCreateReaderForm(Model model) {
        model.addAttribute("reader", new Reader());
        return "reader_add-edit";
    }

    @PostMapping({ "/save_reader", "/save_reader/" })
    public String saveReader(@ModelAttribute("reader") Reader reader, Model model) {
        try {
            readerService.save(reader);
        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("reader", reader);
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                model.addAttribute("error",
                        "Пользователь с таким номером телефона уже существует. Пожалуйста, укажите другой номер.");
            } else {
                model.addAttribute("error", e.getMessage());
            }
            return "reader_add-edit";
        }
        return "redirect:/admin/readers";
    }

    @DeleteMapping({ "/delete_reader/{id}", "/delete_reader/{id}/" })
    public String deleteReader(Model model, @PathVariable int id) {
        readerService.deleteReader(id);
        return "redirect:/admin/readers";
    }

    @GetMapping({ "/edit/{id}", "/edit/{id}/" })
    public String editReader(@PathVariable int id, Model model) {
        model.addAttribute("reader", readerService.findByIDReader(id));
        return "reader_add-edit";
    }

    @PatchMapping({ "/update_reader", "/update_reader/" })
    public String updateReader(@ModelAttribute("reader") Reader reader, Model model) {

        try {
            readerService.updateReader(reader);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("reader", reader);
            model.addAttribute("error", e.getMessage());
            return "reader_add-edit";
        }
        return "redirect:/admin/readers";
    }

    @GetMapping("/{id}/cart/new")
    public String newBookToReaderCart(@PathVariable int id, Model model) {
        Reader reader = readerService.findByIDReader(id);
        List<Book> books = bookService.findByQuantityNotZeroAndDeletedFalse();
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);

        return "reader_add_to_cart_book";
    }

    @PostMapping("/{id}/cart/new")
    public String addBookToReaderCart(@PathVariable int id, @ModelAttribute("bookId") int bookId, Model model) {
        Reader reader = readerService.findByIDReader(id);
        Book book = bookService.findById(bookId);
        cartElementService.save(new CartElement(0, reader, book));

        return "redirect:/admin/readers/%d/cart".formatted(id);
    }

}
