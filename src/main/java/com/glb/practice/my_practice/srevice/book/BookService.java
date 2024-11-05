package com.glb.practice.my_practice.srevice.book;

import java.util.List;



import com.glb.practice.my_practice.models.Book;


public interface BookService {
    public List<Book> getBooks();
    Book saveBook(Book book);
    Book findByIDBook(int id);
    Book updateBook(Book book);
    void deleteBook(int id);
}
