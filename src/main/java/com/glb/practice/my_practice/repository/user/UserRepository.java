package com.glb.practice.my_practice.repository.user;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.glb.practice.my_practice.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

   
    User findByUsername(String username); 
    List<User> findByRole(String role, Sort sort);
}
