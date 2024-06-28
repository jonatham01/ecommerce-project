package com.auth_service.service;

import com.auth_service.Exception.InvalidPasswordException;
import com.auth_service.dto.SaveUser;
import com.auth_service.entity.User;
import com.auth_service.repository.UserRepository;
import com.auth_service.util.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository repository, final PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void validatePassword(final SaveUser user) {
        if(!StringUtils.hasText(user.getPassword())
                ||
                !StringUtils.hasText(user.getRepeatedPassword())){
            throw new InvalidPasswordException("Password is empty");
        }
        if(!user.getRepeatedPassword().equals(user.getPassword())){
            throw new InvalidPasswordException("Passwords do not match");
        }
    }

    public User createUser(SaveUser newUser) {
        validatePassword(newUser);
        User user = new User();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(Role.ADMINISTRATOR);
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }
}
