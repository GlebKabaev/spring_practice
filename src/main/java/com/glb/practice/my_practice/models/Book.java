package com.glb.practice.my_practice.models;



import java.util.Set;

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
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "deposit_amount", nullable = false)
    private double depositAmount;
    @Column(name = "rental_cost", nullable = false)
    private double rentalCost;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Rental> rentals;
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", quantity=" + quantity +
                ", depositAmount=" + depositAmount +
                ", rentalCost=" + rentalCost +
                '}';
    }
    public Book(){

    }
}
