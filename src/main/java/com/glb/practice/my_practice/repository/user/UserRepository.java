package com.glb.practice.my_practice.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    
}
