package com.glb.practice.my_practice.models;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

import lombok.*;
@Data
@Builder
@Entity
@Table(name = "rentals")
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reader_Id")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_Id")
    private Book book;

    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;


    @Column(name = "expected_return_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedReturnDate;
}
