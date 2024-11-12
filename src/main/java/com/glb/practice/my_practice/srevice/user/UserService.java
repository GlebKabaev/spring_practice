package com.glb.practice.my_practice.srevice.user;

import java.util.List;

import com.glb.practice.my_practice.models.User;

public interface UserService {
    public List<User> getUsers(String field);
    User saveUser(User user);
    User findByIDUser(int id);
    User updateUser(User user);
    void deleteUser(int id);  
} 
