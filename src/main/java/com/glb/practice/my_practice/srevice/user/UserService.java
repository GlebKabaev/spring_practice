package com.glb.practice.my_practice.srevice.user;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.models.UserReader;
import com.glb.practice.my_practice.repository.user.UserRepository;
import com.glb.practice.my_practice.srevice.userReader.UserReaderService;

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
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReaderService userReaderService;
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username); // Ищем пользователя в базе данных
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user); // Возвращаем CustomUserDetails с данными User
    }

    @Transactional(readOnly = true)
    public List<User> getUsers(String field) {
        return userRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }

    @Transactional
    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByIdUser(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User updateUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public UserReader addReaderForUser(User user, Reader reader) {
        UserReader userReader=new UserReader();
        userReader.setReader(reader);
        userReader.setUser(user);
        return userReaderService.save(userReader);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    public User thisUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = findByUserName(userDetails.getUsername());
        return user;
    }
}
