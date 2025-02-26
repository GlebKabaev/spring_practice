package com.glb.practice.my_practice.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

import lombok.*;
//TODO: установить nullable везде где надо
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

    @ManyToOne(fetch = FetchType.EAGER)
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

    @Column(name = "returned", nullable = false, columnDefinition = "boolean default false")
    private Boolean returned; // true - книга возвращена, false - арендована
}
