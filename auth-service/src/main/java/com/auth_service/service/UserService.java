package com.auth_service.service;

import com.auth_service.Exception.InvalidPasswordException;
import com.auth_service.dto.RegisteredUser;
import com.auth_service.dto.SaveUser;
import com.auth_service.entity.User;
import com.auth_service.mapper.UserMapper;
import com.auth_service.repository.UserRepository;
import com.auth_service.util.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(final UserRepository repository,
                       final PasswordEncoder passwordEncoder,
                       final UserMapper userMapper) {
        this.userRepository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userMapper = new UserMapper();

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

    public RegisteredUser createUser(SaveUser newUser) {
        validatePassword(newUser);
        User user = new User();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(Role.ADMINISTRATOR);
        User userCreated = userRepository.save(user);
        return userMapper.fromUserToRegisteredUser(userCreated);
    }

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public User findLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        return findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
