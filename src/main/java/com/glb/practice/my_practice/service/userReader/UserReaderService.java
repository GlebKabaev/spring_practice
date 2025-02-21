package com.glb.practice.my_practice.service.userReader;

import java.util.List;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.models.UserReader;

public interface UserReaderService {
    public List<UserReader> findAll();

    UserReader save(UserReader userReader);

    UserReader findById(int id);

    UserReader update(UserReader userReader);

    void deleteById(int id);

    public UserReader findByReader(Reader reader);

    public UserReader findByUser(User user);
}
