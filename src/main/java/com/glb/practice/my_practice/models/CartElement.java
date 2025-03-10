package com.glb.practice.my_practice.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "carts")
@AllArgsConstructor
@NoArgsConstructor
public class CartElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}
