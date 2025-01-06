package com.glb.practice.my_practice.srevice.user;

import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.repository.user.UserRepository;
import com.glb.practice.my_practice.auth.CustomUserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);  // Ищем пользователя в базе данных
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user);  // Возвращаем CustomUserDetails с данными User
    }
}
