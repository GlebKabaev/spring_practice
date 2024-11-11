package com.glb.practice.my_practice.srevice.book;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Book;
import com.glb.practice.my_practice.repository.book.BookRepository;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
@Primary
public class BookSeviceImpl implements BookService{
    private final BookRepository BOOK_REPOSITORIY;

    @Override
    public List<Book> getBooks(String field) {
        //return BOOK_REPOSITORIY.findAll(Sort.by(Sort.Direction.DESC, field));
        return BOOK_REPOSITORIY.findAll(Sort.by(Sort.Order.asc(field)));
    }
    @Override
    public List<Book> getNotZeroBooks(){
        return BOOK_REPOSITORIY.findByQuantityNot(0, Sort.by(Sort.Direction.DESC, "id"));

    }

    @Override
    public Book saveBook(Book book) {
        if(book.getQuantity()>=0 && book.getRentalCost()>=0 && book.getDepositAmount()>=0){
            return BOOK_REPOSITORIY.save(book);

        }else{
            throw new IllegalArgumentException("поле не может быть отрицательным");
        }
    }

    @Override
    public Book findByIDBook(int id) {
        return BOOK_REPOSITORIY.findById(id).get();
    }

    @Override
    public Book updateBook(Book book) {
        if(book.getQuantity()>=0 && book.getRentalCost()>=0 && book.getDepositAmount()>=0){
            return BOOK_REPOSITORIY.save(book);

        }else{
            throw new IllegalArgumentException("поле не может быть отрицательным");
        }
    }

    @Override
    public void deleteBook(int id) {
        BOOK_REPOSITORIY.deleteById(id);
    }
    
    

    
}
