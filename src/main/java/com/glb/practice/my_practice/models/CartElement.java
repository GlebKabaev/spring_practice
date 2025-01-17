package com.glb.practice.my_practice.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "carts")
@AllArgsConstructor
public class CartElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    public CartElement(){

    }
}
