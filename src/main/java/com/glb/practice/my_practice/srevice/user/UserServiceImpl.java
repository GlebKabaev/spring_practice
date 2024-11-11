package com.glb.practice.my_practice.srevice.user;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.repository.user.UserRepository;

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
        user.setPhone(validateAndFormatPhoneNumber(user.getPhone()));
        return userRepository.save(user);
    }

    @Override
    public User findByIDUser(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(User user) {
        user.setPhone(validateAndFormatPhoneNumber(user.getPhone()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
    private String validateAndFormatPhoneNumber(String phone) {
        // Проверка на формат +7(999)999-99-99
        if (phone.startsWith("+7(") && phone.length() == 16 &&
            phone.charAt(6) == ')' && phone.charAt(10) == '-' && phone.charAt(13) == '-') {
    
            for (int i = 0; i < phone.length(); i++) {
                if (i != 0 && i != 2 && i != 3 && i != 6 && i != 10 && i != 13) {
                    if (!Character.isDigit(phone.charAt(i))) {
                        throw new IllegalArgumentException("Неверный формат номера телефона");
                    }
                }
            }
            return phone; // Номер уже в валидном формате
        }
        
        // Проверка на формат 89999999999
        else if (phone.startsWith("8") && phone.length() == 11) {
            for (int i = 1; i < phone.length(); i++) {
                if (!Character.isDigit(phone.charAt(i))) {
                    throw new IllegalArgumentException("Неверный формат номера телефона");
                }
            }
            // Конвертируем формат 89999999999 в +7(999)999-99-99
            return "+7(" + phone.substring(1, 4) + ")" + phone.substring(4, 7) + "-" + phone.substring(7, 9) + "-" + phone.substring(9, 11);
        } else {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }
    }
    
    
}
