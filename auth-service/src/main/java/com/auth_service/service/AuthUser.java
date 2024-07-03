package com.auth_service.service;

import com.auth_service.dto.AuthenticationRequest;
import com.auth_service.dto.AuthenticationResponse;
import com.auth_service.dto.RegisteredUser;
import com.auth_service.dto.SaveUser;
import com.auth_service.entity.JwtToken;
import com.auth_service.entity.User;
import com.auth_service.mapper.UserMapper;
import com.auth_service.repository.JwtTokenRepository;
import com.auth_service.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class AuthUser {

    private final JwtTokenSevice jwtTokenSevice;
    private final UserRepository userRepository;
    private final JwtTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;



    public AuthUser(JwtTokenSevice jwtTokenSevice,
                    UserRepository userRepository,
                    JwtTokenRepository tokenRepository,
                    AuthenticationManager authenticationManager,
                    UserMapper userMapper){
        this.jwtTokenSevice = jwtTokenSevice;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public AuthenticationResponse authenticateOneCustomer(AuthenticationRequest authenticationRequest){
        Authentication auth = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        authenticationManager.authenticate(auth);
        User user = (User) auth.getPrincipal();
        String jwt = jwtTokenSevice.generateToken(user, userMapper.fromUserToClaims(user));
        return new AuthenticationResponse(jwt);
    }

    public void logoutOneCustomer(HttpServletRequest request){
        String token = jwtTokenSevice.getJwtFromRequest(request);
        if(!StringUtils.hasText(token))return;
        Optional<JwtToken> jwtToken = tokenRepository.findByToken(token);
        if(jwtToken.isPresent() && jwtToken.get().isValid()){
            jwtToken.get().setValid(false);
            tokenRepository.save(jwtToken.get());
        }
    }








}
