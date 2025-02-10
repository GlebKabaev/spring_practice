package com.glb.practice.my_practice.controllers.userReader;

import com.glb.practice.my_practice.models.*;
import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.cart.CartElementService;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.user.UserService;
import com.glb.practice.my_practice.srevice.userReader.UserReaderService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//TODO: переделать все mapping на верные
//TODO:изучить restfullapi еще больше
//TODO:создать сущность user reader
@Controller
@RequestMapping("/admin/users-readersV2")
@AllArgsConstructor
public class UserReaderViewController {
    private final UserReaderService userReaderService;
    private final CartElementService cartElementService;
    private final BookService bookService;
    private final UserService us;

    /** Показать список пользователей и читателей */
    // сортировку добавить
    @GetMapping({ "/", "" })
    public String showUsersAndReaders(Model model) {
        //List<String> sortFields = Arrays.asList("id", "username", "Фамилия", "Имя", "Отчество");
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
    public String showUserAndReader(@PathVariable int userId, Model model) {
        model.addAttribute("userReader", userReaderService.findById(userId));
        return "user_reader";
    }

    /** Форма создания нового пользователя с читателем */
    @GetMapping("/new")
    public String showCreateUserReaderForm(Model model) {
        model.addAttribute("userReader", new UserReader());
        return "user_reader_add-edit";
    }

    /** Сохранение нового пользователя с читателем */
    @PostMapping("/save")
    public String saveUserAndReader(@ModelAttribute("userReader") UserReader userReader,
            Model model) {
        try {
            userReaderService.save(userReader);
        } catch (Exception e) {
            model.addAttribute("user", userReader);
            model.addAttribute("error", e.getMessage());
            return "user_reader_add-edit";
        }
        return "redirect:/admin/users-readersV2";
    }

    /** Удаление пользователя и его читателя */
    @GetMapping("/delete/{userReaderID}")
    public String deleteUserAndReader(@PathVariable int userId) {
        us.deleteUser(userId);
        return "redirect:/admin/users-readersV2";
    }

    /** Форма редактирования пользователя и его читателя */
    @GetMapping("/edit/{userReaderID}")
    public String editUserAndReader(@PathVariable int userId, Model model) {
        UserReader userReader = userReaderService.findById(userId);
        model.addAttribute("userReader", userReader);
        return "user_reader_add-edit";
    }

    /** Обновление данных пользователя и его читателя */
    // TODO проблема изменения reader так как он принимает id значение своего user
    @PostMapping("/update")
    public String updateUserAndReader(@ModelAttribute("userReader") UserReader userReader,
            Model model) {
        try {
           userReaderService.update(userReader);
        } catch (Exception e) {
            model.addAttribute("userReader", userReader);
            model.addAttribute("error", e.getMessage());
            return "user_reader_add-edit";
        }
        return "redirect:/admin/users-readersV2";
    }

    /** Просмотр корзины читателя */
    @GetMapping("/{userReaderID}/cart")
    public String showReaderCart(@PathVariable int userReaderId, Model model) {
        UserReader userReader=userReaderService.findById(userReaderId);
        Reader reader=userReader.getReader();
        List<CartElement> cartElements = cartElementService.findByReaderId(reader.getId());

        model.addAttribute("reader", reader);
        model.addAttribute("cart_elements", cartElements);
        return "admin_reader_cart";
    }

    /* Форма добавления книги в корзину */
    @GetMapping("/{userReaderID}/cart/new")
    public String newBookToReaderCart(@PathVariable int userReaderId, Model model) {
        UserReader userReader=userReaderService.findById(userReaderId);
        Reader reader=userReader.getReader();

        List<Book> books = bookService.findByQuantityNotZeroAndDeletedFalse();
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);
        return "reader_add_to_cart_book";
    }

    /** Добавление книги в корзину читателя */
    @PostMapping("/{userReaderID}/cart/new")
    public String addBookToReaderCart(@PathVariable int userReaderId, @RequestParam("bookId") int bookId) {
        UserReader userReader=userReaderService.findById(userReaderId);
       

        Reader reader = userReader.getReader();
        Book book = bookService.findById(bookId);
        cartElementService.save(new CartElement(0, reader, book));

        return "redirect:/admin/users-readersV2/%d/cart".formatted(userReaderId);
    }
}
