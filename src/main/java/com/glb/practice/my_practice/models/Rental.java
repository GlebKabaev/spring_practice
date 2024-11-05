package com.glb.practice.my_practice.models;
import java.util.Date;

import jakarta.persistence.*;

import lombok.*;
@Data
@Builder
@Entity
@Table(name = "rentals")
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reader_Id")
    private User reader;

    @ManyToOne
    @JoinColumn(name = "book_Id")
    private Book book;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    private Date expectedReturnDate;
    Rental(){
        
    }
}
