package com.glb.practice.my_practice.srevice.user.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.repository.user.UserRepository;
import com.glb.practice.my_practice.srevice.user.UserService;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        
       return userRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByIDUser(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
    
}
