package com.glb.practice.my_practice.models;



import jakarta.persistence.*;

import lombok.*;
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "genere", nullable = false)
    private String genere;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "depositAmount", nullable = false)
    private double depositAmount;
    @Column(name = "rentalCost", nullable = false)
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
}
