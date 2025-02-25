package com.glb.practice.my_practice.service.reader;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glb.practice.my_practice.models.Reader;
import com.glb.practice.my_practice.models.UserReader;
import com.glb.practice.my_practice.repository.reader.ReaderRepository;
import com.glb.practice.my_practice.repository.user.UserRepository;
import com.glb.practice.my_practice.service.user.UserService;
import com.glb.practice.my_practice.service.userReader.UserReaderService;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Primary
public class ReaderServiceImpl implements ReaderService {
    ReaderRepository readerRepository;
    UserRepository userRepository;
    UserReaderService userReaderService;
    UserService userService;
    // TODO сделать нормальное имя для всех методов всех сервисов
    @Transactional(readOnly = true)
    @Override
    public List<Reader> findAll(String field) {

        return readerRepository.findAll(Sort.by(Sort.Order.asc(field)));
    }

    @Transactional
    @Override
    public Reader save(Reader reader) {
        reader.setPhone(validateAndFormatPhoneNumber(reader.getPhone()));
        if (readerRepository.existsByPhone(reader.getPhone())) {
            throw new IllegalArgumentException("Читатель с таким номером уже существует.");
        }
        return readerRepository.save(reader);
    }

    @Transactional(readOnly = true)
    @Override
    public Reader findByIDReader(int id) {
        return readerRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Reader updateReader(Reader reader) {
        reader.setPhone(validateAndFormatPhoneNumber(reader.getPhone()));
        return readerRepository.save(reader);
    }

    @Transactional
    @Override
    public void deleteReader(int id) {
        readerRepository.deleteById(id);
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
            return "+7(" + phone.substring(1, 4) + ")" + phone.substring(4, 7) + "-" + phone.substring(7, 9) + "-"
                    + phone.substring(9, 11);
        }

        // Проверка на формат +79999999999
        else if (phone.startsWith("+7") && phone.length() == 12) {
            for (int i = 2; i < phone.length(); i++) {
                if (!Character.isDigit(phone.charAt(i))) {
                    throw new IllegalArgumentException("Неверный формат номера телефона");
                }
            }
            // Конвертируем формат +79999999999 в +7(999)999-99-99
            return "+7(" + phone.substring(2, 5) + ")" + phone.substring(5, 8) + "-" + phone.substring(8, 10) + "-"
                    + phone.substring(10, 12);
        } else {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }
    }

    @Override
    public Reader thisReader() {
        UserReader userReader = userReaderService.findByUser(userService.thisUser());
        return userReader.getReader();
    }
    

}
