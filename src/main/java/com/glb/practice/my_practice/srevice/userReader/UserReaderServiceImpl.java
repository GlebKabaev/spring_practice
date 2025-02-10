package com.glb.practice.my_practice.srevice.userReader;

import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.User;
import com.glb.practice.my_practice.models.UserReader;
import com.glb.practice.my_practice.repository.userReader.UserReaderRepository;

@Service
@AllArgsConstructor
@Primary
public class UserReaderServiceImpl implements UserReaderService {
    UserReaderRepository userReaderRepository;

    @Override
    public List<UserReader> findAll() {
        return userReaderRepository.findAll();
    }

    @Override
    public UserReader save(UserReader userReader) {
        return userReaderRepository.save(userReader);
    }

    @Override
    public UserReader findById(int id) {
        return userReaderRepository.findById(id).get();
    }

    @Override
    public UserReader update(UserReader userReader) {
        return userReaderRepository.save(userReader);
    }

    @Override
    public void deleteById(int id) {
        userReaderRepository.deleteById(id);
    }

    @Override
    public UserReader findByReader(Reader reader) {
        return userReaderRepository.findByReader(reader);
    }

    @Override
    public UserReader findByUser(User user) {
        return userReaderRepository.findByUser(user);
    }

}
