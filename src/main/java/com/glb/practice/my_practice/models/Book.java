package com.glb.practice.my_practice.models;



import jakarta.persistence.*;

import lombok.*;
@Data
@Builder
@Entity
@Table(name = "books")
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "genere", nullable = false)
    private String genere;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "depositamount", nullable = false)
    private double depositAmount;
    @Column(name = "rentalcost", nullable = false)
    private double rentalCost;
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genere='" + genere + '\'' +
                ", quantity=" + quantity +
                ", depositAmount=" + depositAmount +
                ", rentalCost=" + rentalCost +
                '}';
    }
    Book(){

    }
}
