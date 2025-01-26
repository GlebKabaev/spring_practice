package com.glb.practice.my_practice.controllers.reader;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.srevice.reader.ReaderService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/readers")
@AllArgsConstructor
public class ReaderViewController {
    private final ReaderService readerService;

    @GetMapping({ "/", "" })
    public String showReaders(Model model) {

        List<String> sortFields = Arrays.asList("id", "Фамилия", "Имя", "Отчество");
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("readers", readerService.getReaders("id"));
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
        model.addAttribute("readers", readerService.getReaders(field));
        return "reader_list";
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public String showReaderData(Model model, @PathVariable int id) {
        // TODO список арендованных книг пользователем и время захода на сайт
        model.addAttribute("reader", readerService.findByIDReader(id));
        return "reader";
    }

    @GetMapping({ "/new", "/new/" })
    public String showCreateReaderForm(Model model) {
        model.addAttribute("reader", new Reader());
        return "reader_add-edit";
    }

    @PostMapping({ "/save_reader", "/save_reader/" })
    public String saveReader(@ModelAttribute("reader") Reader reader, Model model) {
        try {
            readerService.saveReader(reader);
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

    @GetMapping({ "/delete_reader/{id}", "/delete_reader/{id}/" })
    public String deleteReader(Model model, @PathVariable int id) {
        readerService.deleteReader(id);
        return "redirect:/admin/readers";
    }

    @GetMapping({ "/edit/{id}", "/edit/{id}/" })
    public String editReader(@PathVariable int id, Model model) {
        model.addAttribute("reader", readerService.findByIDReader(id));
        return "reader_add-edit";
    }

    @PostMapping({ "/update_reader", "/update_reader/" })
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
}
