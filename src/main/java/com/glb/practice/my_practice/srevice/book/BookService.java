package com.glb.practice.my_practice.srevice.book;

import java.util.List;



import com.glb.practice.my_practice.models.Book;


public interface BookService {
    List<Book> findAll(String field);
    List<Book> findByQuantityNotZeroAndDeletedFalse();
    List<Book> getNotZeroSortedBooks(String field);
    Book save(Book book);
    Book findById(int id);
    Book update(Book book);
    void deleteById(int id);
}
