package com.glb.practice.my_practice.models;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
@Data
@Builder
@Entity
@Table(name = "readers")
@AllArgsConstructor
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Last_Name", nullable = false)
    private String lastName ;
    @Column(name="First_Name", nullable = false)
    private String firstName;
    @Column(name = "Middle_Name", nullable = false)
    private String middleName;
    @Column(name = "Address", nullable = false)
    private String address;
    @Column(name = "Phone", nullable = false)
    private String phone;
    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private Set<Access> accessRecords;
    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private Set<Rental> rentals;
    public String toString(){
        return "Reader{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                '}';
    }
    public Reader(){
       
    }
}
