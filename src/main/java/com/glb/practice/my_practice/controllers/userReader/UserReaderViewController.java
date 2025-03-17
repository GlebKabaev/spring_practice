package com.glb.practice.my_practice.controllers.userReader;

import com.glb.practice.my_practice.models.*;
import com.glb.practice.my_practice.service.reader.ReaderService;
import com.glb.practice.my_practice.service.user.UserService;
import com.glb.practice.my_practice.service.userReader.UserReaderService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//TODO:изучить restfullapi еще больше
@Controller
@RequestMapping("/admin/users-readers")
@AllArgsConstructor
public class UserReaderViewController {
    private final UserReaderService userReaderService;
    private final UserService userService;
    private final ReaderService readerService;

    /** Показать список пользователей и читателей */
    @GetMapping({ "/", "" })
    public String showUsersAndReaders(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model,
            @RequestParam(value = "field", defaultValue = "id") String field,
            @RequestParam(value = "search", required = false) String searchQuery) {
        Page<UserReader> userReaderPage = userReaderService.findPaginated(page, size,field,searchQuery);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userReaderPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("usersReaders", userReaderPage.getContent());
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
        model.addAttribute("userReader", new UserReader(null, new User(), new Reader()));
        return "user_reader_add-edit";
    }

    @PostMapping("/save")
    public String saveUserAndReader(@ModelAttribute("userReader") UserReader userReader,
            Model model) {
        try {
            userReader.getUser().setRole("ROLE_USER");
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

    @DeleteMapping("/{userReaderId}/delete")
    public String deleteUserAndReader(@PathVariable int userReaderId) {
        userService.deleteUser(userReaderService.findById(userReaderId).getUser().getId());
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

}
