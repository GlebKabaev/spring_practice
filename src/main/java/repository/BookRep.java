package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.Book;

public interface BookRep extends JpaRepository<Book, Integer> {
    
}
