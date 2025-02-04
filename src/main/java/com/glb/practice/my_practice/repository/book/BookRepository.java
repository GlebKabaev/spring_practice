package com.glb.practice.my_practice.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import com.glb.practice.my_practice.models.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByQuantityNotAndDeletedFalse(int quantity, Sort sort);

}
