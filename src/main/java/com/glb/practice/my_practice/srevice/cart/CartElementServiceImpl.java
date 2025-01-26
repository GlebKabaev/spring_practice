package com.glb.practice.my_practice.srevice.cart;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.repository.cart.CartElementRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class CartElementServiceImpl implements CartElementService {
    CartElementRepository cartRepository;

    @Override
    public List<CartElement> getCartElements(String field) {
        
       return cartRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }

    @Override
    public CartElement saveCartElement(CartElement cart) {
        return cartRepository.save(cart);
    }

    @Override
    public CartElement findByIDCartElement(int id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public CartElement updateCartElement(CartElement cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCartElement(int id) {
        cartRepository.deleteById(id);
    }
    public List<CartElement> getCartElementsByReaderId(int id) {
        
        return cartRepository.findByReaderId(id);
    }

    
}
