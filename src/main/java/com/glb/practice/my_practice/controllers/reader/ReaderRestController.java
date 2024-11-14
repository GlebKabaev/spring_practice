package com.glb.practice.my_practice.controllers.reader;
import java.util.List;


import org.springframework.web.bind.annotation.*;


import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.srevice.reader.ReaderService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/readers")
@AllArgsConstructor
public class ReaderRestController {
    private final ReaderService readerService;
    
    @GetMapping({"/",""})
    public List<Reader> getReaders() {
        return readerService.getReaders("id");
    }
    @PostMapping({"/save_reader","/save_reader/"})
    public Reader saveReader(@RequestBody Reader reader){
        return readerService.saveReader(reader);
    }
    @GetMapping({"/{id}","/{id}/"})
    public Reader findByIDReader(@PathVariable int id){
        return readerService.findByIDReader(id);
    }
    @PutMapping({"/update_reader", "/update_reader/"})
    public Reader updateReader(@RequestBody Reader reader){
        return readerService.updateReader(reader);
    }
    @DeleteMapping({"delete_reader/{id}","delete_reader/{id}/"})
    public void deleteReader(@PathVariable int id){
        readerService.deleteReader(id);
    }
    
}
