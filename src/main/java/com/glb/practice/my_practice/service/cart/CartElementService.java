package com.glb.practice.my_practice.service.cart;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glb.practice.my_practice.models.CartElement;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.repository.cart.CartElementRepository;
import com.glb.practice.my_practice.service.reader.ReaderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class CartElementService {
    CartElementRepository cartRepository;
    ReaderService readerService;
    @Transactional(readOnly = true)
    
    public List<CartElement> findAll(String field) {

        return cartRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }

    @Transactional
    
    public CartElement save(CartElement cart) {
        return cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    
    public CartElement findById(int id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("CartElement not found"));
    }

    @Transactional
    
    public CartElement update(CartElement cart) {
        return cartRepository.save(cart);
    }

    @Transactional
    
    public void deleteById(int id) {
        cartRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    
    public List<CartElement> findByReaderId(int id) {

        return cartRepository.findByReaderId(id);
    }

    @Transactional(readOnly = true)
    
    public List<CartElement> findByBookId(int id) {

        return cartRepository.findByBookId(id);
    }

    public void deleteIfOwner(int id){
        CartElement cartElement = findById(id);
        Reader reader = readerService.thisReader();
        if (reader.getId().equals(cartElement.getReader().getId())) {
            deleteById(id);
        }
    }
    

}
