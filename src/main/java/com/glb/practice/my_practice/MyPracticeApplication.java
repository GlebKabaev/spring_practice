package com.glb.practice.my_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.glb.practice.my_practice.models.Book;

@SpringBootApplication
public class MyPracticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyPracticeApplication.class, args);
        Book book=new Book().setTitle("title")
                            .setAuthor("author")
                            .setGenere("genere")
                            .setQuantity(2)
                            .setDepositAmount(2.1)
                            .setRentalCost(3.1);
                            System.out.println(book);
        
        
    }
}
