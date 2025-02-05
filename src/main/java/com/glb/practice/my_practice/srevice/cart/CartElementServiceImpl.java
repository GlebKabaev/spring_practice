package com.glb.practice.my_practice.srevice.cart;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.repository.cart.CartElementRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class CartElementServiceImpl implements CartElementService {
    CartElementRepository cartRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CartElement> findAll(String field) {

        return cartRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }

    @Transactional
    @Override
    public CartElement save(CartElement cart) {
        return cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    @Override
    public CartElement findById(int id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("CartElement not found"));
    }

    @Transactional
    @Override
    public CartElement update(CartElement cart) {
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        cartRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CartElement> findByReaderId(int id) {

        return cartRepository.findByReaderId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CartElement> findByBookId(int id) {

        return cartRepository.findByBookId(id);
    }

}
