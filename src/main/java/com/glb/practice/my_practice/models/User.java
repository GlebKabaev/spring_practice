package com.glb.practice.my_practice.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Column(name = "role")
    private String role;

    // Конструктор по умолчанию
    public User() {
    }
}
