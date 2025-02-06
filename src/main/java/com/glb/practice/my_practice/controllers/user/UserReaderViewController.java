package com.glb.practice.my_practice.controllers.user;

import com.glb.practice.my_practice.models.*;
import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.cart.CartElementService;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users-readers")
@AllArgsConstructor
public class UserReaderViewController {
    private final UserService userService;
    private final ReaderService readerService;
    private final CartElementService cartElementService;
    private final BookService bookService;

    /** Показать список пользователей и читателей */
    @GetMapping({"/", ""})
    public String showUsersAndReaders(Model model) {
        List<String> sortFields = Arrays.asList("id", "username", "Фамилия", "Имя", "Отчество");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("users", userService.getUsers("id"));
        model.addAttribute("readers", readerService.findAll("id"));
        return "user_reader_list";
    }

    /** Сортировка пользователей и читателей */
    @GetMapping("/sort")
    public String sortUsersAndReaders(@RequestParam("field") String field, Model model) {
        List<String> sortFields = Arrays.asList("id", "username", "Фамилия", "Имя", "Отчество");

        switch (field) {
            case "Фамилия" -> field = "lastName";
            case "Имя" -> field = "firstName";
            case "Отчество" -> field = "middleName";
        }

        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("users", userService.getUsers("id"));
        model.addAttribute("readers", readerService.findAll(field));
        return "user_reader_list";
    }

    /** Просмотр информации о пользователе и его читателе */
    @GetMapping("/{userId}")
    public String showUserAndReader(@PathVariable int userId, Model model) {
        User user = userService.findByIdUser(userId);
        Optional<Reader> reader = Optional.ofNullable(user.getReader());

        model.addAttribute("user", user);
        model.addAttribute("reader", reader.orElse(null));
        return "user_reader";
    }

    /** Форма создания нового пользователя с читателем */
    @GetMapping("/new")
    public String showCreateUserReaderForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("reader", new Reader());
        return "user_reader_add-edit";
    }

    /** Сохранение нового пользователя с читателем */
    @PostMapping("/save")
    public String saveUserAndReader(@ModelAttribute("user") User user,
                                    @ModelAttribute("reader") Reader reader,
                                    Model model) {
        try {
            readerService.save(reader);
            user.setReader(reader);
            userService.saveUser(user);
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("reader", reader);
            model.addAttribute("error", e.getMessage());
            return "user_reader_add-edit";
        }
        return "redirect:/admin/users-readers";
    }

    /** Удаление пользователя и его читателя */
    @GetMapping("/delete/{userId}")
    public String deleteUserAndReader(@PathVariable int userId) {
        User user = userService.findByIdUser(userId);
        if (user.getReader() != null) {
            readerService.deleteReader(user.getReader().getId());
        }
        userService.deleteUser(userId);
        return "redirect:/admin/users-readers";
    }

    /** Форма редактирования пользователя и его читателя */
    @GetMapping("/edit/{userId}")
    public String editUserAndReader(@PathVariable int userId, Model model) {
        User user = userService.findByIdUser(userId);
        Reader reader = user.getReader();

        model.addAttribute("user", user);
        model.addAttribute("reader", reader != null ? reader : new Reader());
        return "user_reader_add-edit";
    }

    /** Обновление данных пользователя и его читателя */
    @PostMapping("/update")
    public String updateUserAndReader(@ModelAttribute("user") User user,
                                      @ModelAttribute("reader") Reader reader,
                                      Model model) {
        try {
            readerService.updateReader(reader);
            user.setReader(reader);
            userService.updateUser(user);
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("reader", reader);
            model.addAttribute("error", e.getMessage());
            return "user_reader_add-edit";
        }
        return "redirect:/admin/users-readers";
    }

    /** Просмотр корзины читателя */
    @GetMapping("/{userId}/cart")
    public String showReaderCart(@PathVariable int userId, Model model) {
        User user = userService.findByIdUser(userId);
        if (user.getReader() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found");
        }

        Reader reader = user.getReader();
        List<CartElement> cartElements = cartElementService.findByReaderId(reader.getId());

        model.addAttribute("reader", reader);
        model.addAttribute("cart_elements", cartElements);
        return "admin_reader_cart";
    }

    /** Форма добавления книги в корзину */
    @GetMapping("/{userId}/cart/new")
    public String newBookToReaderCart(@PathVariable int userId, Model model) {
        User user = userService.findByIdUser(userId);
        if (user.getReader() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found");
        }

        List<Book> books = bookService.findByQuantityNotZeroAndDeletedFalse();
        model.addAttribute("reader", user.getReader());
        model.addAttribute("books", books);
        return "reader_add_to_cart_book";
    }

    /** Добавление книги в корзину читателя */
    @PostMapping("/{userId}/cart/new")
    public String addBookToReaderCart(@PathVariable int userId, @RequestParam("bookId") int bookId) {
        User user = userService.findByIdUser(userId);
        if (user.getReader() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found");
        }

        Reader reader = user.getReader();
        Book book = bookService.findById(bookId);
        cartElementService.save(new CartElement(0, reader, book));

        return "redirect:/admin/users-readers/%d/cart".formatted(userId);
    }
}
