package com.glb.practice.my_practice.auth;

import com.glb.practice.my_practice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // добавить роли или разрешения, связанные с пользователем
        return List.of(() -> "USER");  // Пример: роль "USER"
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // Получаем пароль из User
    }

    @Override
    public String getUsername() {
        return user.getUsername();  // Получаем имя пользователя из User
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Логика проверки на срок действия аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Логика проверки на блокировку аккаунта
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Логика проверки на истечение срока действия учетных данных
    }

    @Override
    public boolean isEnabled() {
        return true;  // Проверка на активность аккаунта
    }

    public User getUser() {
        return user;  // Доступ к User
    }
}
