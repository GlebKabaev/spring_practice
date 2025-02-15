package com.glb.practice.my_practice.models;

import jakarta.persistence.*;

import lombok.*;

@Data
@Builder
@Entity
@Table(name = "usersReaders")
@AllArgsConstructor
@NoArgsConstructor
public class UserReader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    @OneToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;
}
