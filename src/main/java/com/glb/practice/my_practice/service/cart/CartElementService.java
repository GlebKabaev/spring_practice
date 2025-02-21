package com.glb.practice.my_practice.service.cart;

import java.util.List;

import com.glb.practice.my_practice.models.CartElement;

public interface CartElementService {
    public List<CartElement> findAll(String field);
    CartElement save(CartElement cart);
    CartElement findById(int id);
    CartElement update(CartElement cart);
    public List<CartElement> findByReaderId(int id);
    public List<CartElement> findByBookId(int id);
    void deleteById(int id);  
} 

