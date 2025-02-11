package com.glb.practice.my_practice.controllers.userReader;

import com.glb.practice.my_practice.models.*;
import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.cart.CartElementService;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.user.UserService;
import com.glb.practice.my_practice.srevice.userReader.UserReaderService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: переделать все mapping на верные
//TODO:изучить restfullapi еще больше
//TODO:создать сущность user reader
@Controller
@RequestMapping("/admin/users-readers")
@AllArgsConstructor
public class UserReaderViewController {
    private final UserReaderService userReaderService;
    private final CartElementService cartElementService;
    private final BookService bookService;
    private final UserService userService;
    private final ReaderService readerService;

    /** Показать список пользователей и читателей */
    // сортировку добавить
    @GetMapping({ "/", "" })
    public String showUsersAndReaders(Model model) {
        // List<String> sortFields = Arrays.asList("id", "username", "Фамилия", "Имя",
        // "Отчество");
        // model.addAttribute("sortFields", sortFields);
        model.addAttribute("UsersReaders", userReaderService.findAll());
        return "user_reader_list";
    }

    // /** Сортировка пользователей и читателей */
    // @GetMapping("/sort")
    // public String sortUsersAndReaders(@RequestParam("field") String field, Model
    // model) {
    // List<String> sortFields = Arrays.asList("id", "username", "Фамилия", "Имя",
    // "Отчество");

    // switch (field) {
    // case "Фамилия" -> field = "lastName";
    // case "Имя" -> field = "firstName";
    // case "Отчество" -> field = "middleName";
    // }

    // model.addAttribute("sortFields", sortFields);
    // model.addAttribute("selectedField", field);
    // model.addAttribute("users", userService.getUsers("id"));
    // model.addAttribute("readers", readerService.findAll(field));
    // return "user_reader_list";
    // }

    /** Просмотр информации о пользователе и его читателе */
    @GetMapping("/{userReaderId}")
    public String showUserAndReader(@PathVariable int userReaderId, Model model) {
        model.addAttribute("userReader", userReaderService.findById(userReaderId));
        return "user_reader";
    }

    /** Форма создания нового пользователя с читателем */
    @GetMapping("/new")
    public String showCreateUserReaderForm(Model model) {
        model.addAttribute("reader", new Reader());
        model.addAttribute("user", new User());
        return "user_reader_add-edit";
    }

    /** Сохранение нового пользователя с читателем */
    @PostMapping("/save")
    public String saveUserAndReader(@ModelAttribute("user") User user, @ModelAttribute("reader") Reader reader,
            Model model) {
        try {
            userService.saveUser(user);
            readerService.save(reader);
            userReaderService.save(new UserReader(0, user, reader));
        } catch (Exception e) {
            model.addAttribute("reader", reader);
            model.addAttribute("user", user);
            model.addAttribute("error", e.getMessage());
            if (user.getId() != null) {
                userService.deleteUser(user.getId());
            }
            if (reader.getId() != null) {
                readerService.deleteReader(reader.getId());
            }
            return "user_reader_add-edit";
        }
        return "redirect:/admin/users-readers";
    }

    /** Удаление пользователя и его читателя */
    @GetMapping("/{userReaderId}/delete")
    public String deleteUserAndReader(@PathVariable int userReaderId) {
        userService.deleteUser(userReaderId);
        return "redirect:/admin/users-readers";
    }

    /** Форма редактирования пользователя и его читателя */
    @GetMapping("/{userReaderId}/edit")
    public String editUserAndReader(@PathVariable int userReaderId, Model model) {
        UserReader userReader = userReaderService.findById(userReaderId);

        model.addAttribute("reader", userReader.getReader());
        model.addAttribute("user", userReader.getUser());
        return "user_reader_add-edit";
    }

    /** Обновление данных пользователя и его читателя */
    // TODO проблема изменения reader так как он принимает id значение своего user
    @PostMapping("/update")
    public String updateUserAndReader(@ModelAttribute("user") User user, @ModelAttribute("reader") Reader reader,
            Model model) {
        try {
            userService.updateUser(user);
            readerService.updateReader(reader);
        } catch (Exception e) {
            model.addAttribute("reader", reader);
            model.addAttribute("user", user);
            model.addAttribute("error", e.getMessage());
            return "user_reader_add-edit";
        }
        return "redirect:/admin/users-readers";
    }

    /** Просмотр корзины читателя */
    @GetMapping("/{userReaderID}/cart")
    public String showReaderCart(@PathVariable int userReaderId, Model model) {
        UserReader userReader = userReaderService.findById(userReaderId);
        Reader reader = userReader.getReader();
        List<CartElement> cartElements = cartElementService.findByReaderId(reader.getId());

        model.addAttribute("reader", reader);
        model.addAttribute("cart_elements", cartElements);
        return "admin_reader_cart";
    }

    /* Форма добавления книги в корзину */
    @GetMapping("/{userReaderID}/cart/new")
    public String newBookToReaderCart(@PathVariable int userReaderId, Model model) {
        UserReader userReader = userReaderService.findById(userReaderId);
        Reader reader = userReader.getReader();

        List<Book> books = bookService.findByQuantityNotZeroAndDeletedFalse();
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);
        return "reader_add_to_cart_book";
    }

    /** Добавление книги в корзину читателя */
    @PostMapping("/{userReaderID}/cart/new")
    public String addBookToReaderCart(@PathVariable int userReaderId, @RequestParam("bookId") int bookId) {
        UserReader userReader = userReaderService.findById(userReaderId);

        Reader reader = userReader.getReader();
        Book book = bookService.findById(bookId);
        cartElementService.save(new CartElement(0, reader, book));

        return "redirect:/admin/users-readers/%d/cart".formatted(userReaderId);
    }
}
