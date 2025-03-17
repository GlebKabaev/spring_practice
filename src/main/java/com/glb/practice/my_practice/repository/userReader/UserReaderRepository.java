package com.glb.practice.my_practice.repository.userReader;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.models.UserReader;

public interface UserReaderRepository extends JpaRepository<UserReader, Integer> {
    UserReader findByReader(Reader reader);
    Page<UserReader> findAll(Pageable pageable); 
    UserReader findByUser(User user);
    Page<UserReader> findByUser_UsernameContainingIgnoreCase(String username, Pageable pageable);
}
