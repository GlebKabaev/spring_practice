package com.glb.practice.my_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {
    
}
