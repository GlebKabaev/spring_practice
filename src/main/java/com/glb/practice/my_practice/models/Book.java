package com.glb.practice.my_practice.models;


import jakarta.persistence.*;

import lombok.*;

@Data
@Builder
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name="deleted")
    private boolean deleted;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

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

    
}
