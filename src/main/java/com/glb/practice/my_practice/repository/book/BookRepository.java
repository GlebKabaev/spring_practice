package com.glb.practice.my_practice.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.glb.practice.my_practice.models.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByQuantityNotAndDeletedFalse(int quantity, Sort sort);
    Page<Book> findByQuantityNotAndDeletedFalse(int quantity, Pageable pageable);
    Page<Book> findAll(Pageable pageable);
    Page<Book> findByQuantityNotAndDeletedFalseAndTitleContainingIgnoreCase(int quantity, String title, Pageable pageable);
}
