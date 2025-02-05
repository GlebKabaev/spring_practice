package com.glb.practice.my_practice.repository.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.CartElement;

public interface CartElementRepository extends JpaRepository<CartElement, Integer> {
    List<CartElement> findByReaderId(Integer readerId); 
    List<CartElement> findByBookId(Integer bookId);
}
