package com.glb.practice.my_practice.srevice.cart;

import java.util.List;

import com.glb.practice.my_practice.models.CartElement;

public interface CartElementService {

    public List<CartElement> getCartElements(String field);
    CartElement saveCartElement(CartElement cart);
    CartElement findByIDCartElement(int id);
    CartElement updateCartElement(CartElement cart);
    void deleteCartElement(int id);  
} 

