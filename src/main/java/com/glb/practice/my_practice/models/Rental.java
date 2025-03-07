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
    @JoinColumn(name = "reader_Id", nullable = false)
    private Reader reader;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_Id", nullable = false)
    private Book book;

    @Column(name = "issue_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    @Column(name = "expected_return_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedReturnDate;

    @Column(name = "returned", nullable = false, columnDefinition = "boolean default false")
    private Boolean returned; // true - книга возвращена, false - арендована
}
