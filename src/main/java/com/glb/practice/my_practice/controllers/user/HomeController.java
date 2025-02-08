package com.glb.practice.my_practice.controllers.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.srevice.book.BookService;
import com.glb.practice.my_practice.srevice.cart.CartElementService;
import com.glb.practice.my_practice.srevice.image.ImageService;
import com.glb.practice.my_practice.srevice.reader.ReaderService;
import com.glb.practice.my_practice.srevice.rental.RentalService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {
    private BookService bookService;
    private CartElementService cartElementService;
    private ReaderService readerService;
    private RentalService rentalService;
    private final ImageService imageService;

    @GetMapping("")
    public String defaultPage() {
        return "redirect:/home/";
    }

    @GetMapping("/rentals")
    public String rentalPage(Model model) {
        Reader thisReader = readerService.thisReader();
        model.addAttribute("rentals", rentalService.findByReader(thisReader));
        return "reader_rentals";
    }

    // TODO добавить ручку "заказы"
    // TODO добавть trow в сервисы
    @GetMapping({ "/home", "/home/" })
    public String sortBooks(@RequestParam(value = "field", required = false, defaultValue = "title") String field,
            Model model) {
        Reader reader = readerService.thisReader();
        List<String> sortFields = Arrays.asList("Название", "Автор", "Депозит", "Стоимость аренды");
        if (sortFields.contains(field)) {
            sortFields.set(sortFields.indexOf(field), sortFields.get(0));
            sortFields.set(0, field);
        }
        switch (field) {
            case "Название":
                field = "title";
                break;
            case "Автор":
                field = "author";
                break;
            case "Депозит":
                field = "depositAmount";
                break;
            case "Стоимость аренды":
                field = "rentalCost";
            default:
                break;
        }
        List<Book> books = bookService.getNotZeroSortedBooks(field);
        Map<Integer, String> bookImages = new HashMap<>();

        for (Book book : books) {
            if (book.getImage() != null) {
                String base64Image = imageService.getImageBase64(book.getImage());
                bookImages.put(book.getId(), base64Image);
            }
        }
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("books", books);
        model.addAttribute("bookImages", bookImages);
        model.addAttribute("username", reader.getFirstName() + " " + reader.getLastName());
        return "goods_list";
    }

    @PostMapping({ "/add_to_cart/{id}", "/add_to_cart/{id}/" })
    public String addToCart(@PathVariable int id,
            @RequestParam(value = "field", required = false, defaultValue = "title") String field) {
        Reader reader = readerService.thisReader();

        CartElement cartElement = new CartElement(0, reader, bookService.findById(id));
        cartElementService.save(cartElement);

        return "redirect:/home?field=" + field;
    }

}