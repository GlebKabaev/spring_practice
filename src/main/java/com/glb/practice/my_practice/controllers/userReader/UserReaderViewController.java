package com.glb.practice.my_practice.controllers.userReader;

import com.glb.practice.my_practice.models.*;
import com.glb.practice.my_practice.service.book.BookService;
import com.glb.practice.my_practice.service.cart.CartElementService;
import com.glb.practice.my_practice.service.reader.ReaderService;
import com.glb.practice.my_practice.service.rental.RentalService;
import com.glb.practice.my_practice.service.user.UserService;
import com.glb.practice.my_practice.service.userReader.UserReaderService;

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
    private final RentalService rentalService;

    /** Показать список пользователей и читателей */
    // сортировку добавить
    @GetMapping({ "/", "" })
    public String showUsersAndReaders(Model model) {
        // List<String> sortFields = Arrays.asList("id", "username", "Фамилия", "Имя",
        // "Отчество");
        // model.addAttribute("sortFields", sortFields);
        model.addAttribute("usersReaders", userReaderService.findAll());
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
        model.addAttribute("userReader", new UserReader(0, new User(), new Reader()));
        return "user_reader_add-edit";
    }

    @PostMapping("/save")
    public String saveUserAndReader(@ModelAttribute("userReader") UserReader userReader,
            Model model) {
        try {
            userService.saveUser(userReader.getUser());
            readerService.save(userReader.getReader());
            userReaderService.save(userReader);
            return "redirect:/admin/users-readers";
        } catch (Exception e) {
            model.addAttribute("userReader", userReader);
            model.addAttribute("error", e.getMessage());
            if (userReader.getUser().getId() != null) {
                userService.deleteUser(userReader.getUser().getId());
                userReader.setUser(new User());
            }
            if (userReader.getReader().getId() != null) {
                readerService.deleteReader(userReader.getReader().getId());
                userReader.setReader(new Reader());
            }
            return "user_reader_add-edit";

        }

    }

    /** Удаление пользователя и его читателя */
    @DeleteMapping("/{userReaderId}/delete")
    public String deleteUserAndReader(@PathVariable int userReaderId) {
        userService.deleteUser(userReaderId);
        return "redirect:/admin/users-readers";
    }

    /** Форма редактирования пользователя и его читателя */
    @GetMapping("/{userReaderId}/edit")
    public String editUserAndReader(@PathVariable int userReaderId, Model model) {
        UserReader userReader = userReaderService.findById(userReaderId);
        if (userReader.getUser() == null) {
            model.addAttribute("usersReaders", userReaderService.findAll());
            model.addAttribute("error", "Связь c id %d не подлежит редактированию".formatted(userReader.getId()));
            return "user_reader_list";
        }
        model.addAttribute("userReader", userReader);

        return "user_reader_add-edit";
    }

    /** Обновление данных пользователя и его читателя */
    @PatchMapping("/update")
    public String updateUserAndReader(@ModelAttribute("userReader") UserReader userReader,
            Model model) {
        try {
            userService.updateUser(userReader.getUser());
            readerService.updateReader(userReader.getReader());
        } catch (Exception e) {
            model.addAttribute("userReader", userReader);
            model.addAttribute("error", e.getMessage());
            return "user_reader_add-edit";
        }
        return "redirect:/admin/users-readers";
    }

    /** Просмотр корзины читателя */
    @GetMapping("/{userReaderID}/cart")
    public String showReaderCart(@PathVariable int userReaderID, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
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
    public String addBookToReaderCart(@PathVariable int userReaderID, @RequestParam("bookId") int bookId) {
        UserReader userReader = userReaderService.findById(userReaderID);

        Reader reader = userReader.getReader();
        Book book = bookService.findById(bookId);
        cartElementService.save(new CartElement(0, reader, book));

        return "redirect:/admin/users-readers/%d/cart".formatted(userReaderID);
    }

    @GetMapping("/{userReaderID}/repair")
    public String repairUser(@PathVariable int userReaderID, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        if (userReader.getUser() == null) {
            model.addAttribute("userReader", userReader);
            model.addAttribute("reader", userReader.getReader());
            model.addAttribute("user", new User());
            return "repair";
        } else {
            return "redirect:/admin/users-readers";
        }
    }

    @PatchMapping("/{userReaderID}/repair")
    public String repair(@PathVariable int userReaderID, Model model, @ModelAttribute("user") User user) {
        UserReader userReader = userReaderService.findById(userReaderID);
        if (userReader.getUser() == null) {
            userService.saveUser(user);
            userReader.setUser(user);
            userReaderService.update(userReader);
            return "redirect:/admin/users-readers";
        } else {
            return "redirect:/admin/users-readers";
        }
    }

    @GetMapping("/{userReaderID}/rentals")
    public String showReaderRentals(@PathVariable int userReaderID, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();
        List<Rental> rentals = rentalService.findByReader(reader);
        model.addAttribute("reader", reader);
        model.addAttribute("userReader", userReader);
        model.addAttribute("rentals", rentals);
        return "admin_reader_rentals";
    }

    @GetMapping("/{userReaderID}/rentals/new")
    public String showCreateRentalForm(@PathVariable int userReaderID, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        model.addAttribute("reader", userReader.getReader());
        model.addAttribute("userReader", userReader);
        model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
        model.addAttribute("rental", new Rental());
        return "user_reader_new_rental";
    }

    @PostMapping("/{userReaderID}/rentals/new")
    public String saveRental(@PathVariable int userReaderID, @ModelAttribute("rental") Rental rental, Model model) {
        UserReader userReader = userReaderService.findById(userReaderID);
        Reader reader = userReader.getReader();
        try {
            rental.setReader(reader);
            rentalService.save(rental);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("reader", reader);
            model.addAttribute("books", bookService.findByQuantityNotZeroAndDeletedFalse());
            model.addAttribute("rental", new Rental());
            model.addAttribute("error", e.getMessage());
            return "user_reader_new_rental";
        }

        return "redirect:/admin/users-readers/%d/rentals".formatted(userReader.getId());
    }

}
