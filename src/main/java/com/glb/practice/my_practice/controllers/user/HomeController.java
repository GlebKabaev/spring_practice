package com.glb.practice.my_practice.controllers.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
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
import com.glb.practice.my_practice.service.book.BookService;
import com.glb.practice.my_practice.service.cart.CartElementService;
import com.glb.practice.my_practice.service.image.ImageService;
import com.glb.practice.my_practice.service.reader.ReaderService;
import com.glb.practice.my_practice.service.rental.RentalService;

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

    // TODO добавть trow в сервисы
    @GetMapping({ "/home", "/home/" })
    public String sortBooks(@RequestParam(value = "field", required = false, defaultValue = "title") String field,
            Model model, @RequestParam(value = "search", required = false) String searchQuery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Reader reader = readerService.thisReader();
        List<String> sortFields = Arrays.asList("Название", "Автор", "Депозит", "Стоимость аренды");
        if (sortFields.contains(field)) {
            sortFields.set(sortFields.indexOf(field), sortFields.get(0));
            sortFields.set(0, field);
        }
        if(page<0 || size<0){
            model.addAttribute("error","Ошибка пагинации" );
            page=0;
            size=10;
            return sortBooks(field,model,searchQuery,page,size);
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
                break;
            default:
                field = "title";
                break;
        }
        Page<Book> books = bookService.findPaginatedSearched(page, size, field, searchQuery);
        Map<Integer, String> bookImages = new HashMap<>();

        for (Book book : books) {
            if (book.getImage() != null) {
                String base64Image = imageService.getImageBase64(book.getImage());
                bookImages.put(book.getId(), base64Image);
            }
        }
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("selectedField", field);
        model.addAttribute("books", books.getContent());
        model.addAttribute("bookImages", bookImages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("username", reader.getFirstName() + " " + reader.getLastName());
        model.addAttribute("search", searchQuery);
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