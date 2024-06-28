package com.auth_service.service;

import com.auth_service.repository.JwtTokenRepository;
import com.auth_service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class AuthUser {

    private final UserService userService;
    private final JwtTokenSevice jwtTokenSevice;
    private final UserRepository userRepository;
    private final JwtTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthUser(UserService userService,
                    JwtTokenSevice jwtTokenSevice,
                    UserRepository userRepository,
                    JwtTokenRepository tokenRepository,
                    AuthenticationManager authenticationManager
                    ){
        this.userService = userService;
        this.jwtTokenSevice = jwtTokenSevice;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }


}
