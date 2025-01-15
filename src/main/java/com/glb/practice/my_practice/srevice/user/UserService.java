package com.glb.practice.my_practice.srevice.user;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.repository.user.UserRepository;

import lombok.AllArgsConstructor;

import com.glb.practice.my_practice.auth.CustomUserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);  // Ищем пользователя в базе данных
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user);  // Возвращаем CustomUserDetails с данными User
    }
    public List<User> getUsers (String field) {
        return userRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }
    public User saveUser(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User findByIdUser(int id){
        return userRepository.findById(id).get();
    }
    public User updateUser(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User addReaderForUser(User user,Reader reader){
        user.setReader(reader);
        return userRepository.save(user);
    }
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }
    public User findByUserName(String userName){
        return userRepository.findByUsername(userName);
    }
    public User thisUser(){
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = findByUserName(userDetails.getUsername());
        return user;
    }
}
