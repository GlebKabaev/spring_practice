package com.glb.practice.my_practice.service.userReader;

import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.models.UserReader;
import com.glb.practice.my_practice.repository.userReader.UserReaderRepository;

@Service
@AllArgsConstructor
@Primary
public class UserReaderService {
    UserReaderRepository userReaderRepository;

    public List<UserReader> findAll() {
        return userReaderRepository.findAll();
    }

    public Page<UserReader> findPaginated(int page, int size, String field, String searchQuery) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(field)));
        if (searchQuery != null && !searchQuery.isEmpty()) {
            return userReaderRepository.findByUser_UsernameContainingIgnoreCase(searchQuery, pageable);
        } else {
            return userReaderRepository.findAll(pageable);
        }
    }

    public UserReader save(UserReader userReader) {
        return userReaderRepository.save(userReader);
    }

    public UserReader findById(int id) {
        return userReaderRepository.findById(id).get();
    }

    public UserReader update(UserReader userReader) {
        return userReaderRepository.save(userReader);
    }

    public void deleteById(int id) {
        userReaderRepository.deleteById(id);
    }

    public UserReader findByReader(Reader reader) {
        return userReaderRepository.findByReader(reader);
    }

    public UserReader findByUser(User user) {
        return userReaderRepository.findByUser(user);
    }

}
