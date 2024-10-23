package com.glb.practice.my_practice.models;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
@Accessors(chain = true)
@Entity
@Table(name = "books")
@Getter
@Setter
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

    public Book() {

    }

    // // Getters and setters
    // public String getTitle() {
    //     return title;
    // }

    // public void setTitle(String title) {
    //     this.title = title;
    // }

    // public String getAuthor() {
    //     return author;
    // }

    // public void setAuthor(String author) {

    //     this.author = author;

    // }

    // public String getGenere() {
    //     return genere;
    // }

    // public void setGenere(String genere) {

    //     this.genere = genere;

    // }

    // public int getId() {
    //     return id;
    // }

    // // Getters and setters

    // public int getQuantity() {
    //     return quantity;
    // }

    // public void setQuantity(int quantity) {
    //     this.quantity = quantity;
    // }

    // public double getDepositAmount() {
    //     return depositAmount;
    // }

    // public void setDepositAmount(double depositAmount) {
    //     this.depositAmount = depositAmount;
    // }

    // public double getRentalCost() {
    //     return rentalCost;
    // }

    // public void setRentalCost(double rentalCost) {
    //     this.rentalCost = rentalCost;
    // }
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
