package com.auth_service.mapper;

import com.auth_service.dto.RegisteredUser;
import com.auth_service.dto.SaveUser;
import com.auth_service.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserMapper {

    public RegisteredUser fromUserToRegisteredUser(User user) {
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId(user.getUserId());
        registeredUser.setUsername(user.getUsername());
        registeredUser.setName(user.getName());
        registeredUser.setRole(user.getRole().name());
        return  registeredUser;
    }

    public Map<String,Object> fromUserToClaims(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        claims.put("name",user.getName());
        claims.put("role",user.getRole().name());
        claims.put("authorities",user.getAuthorities());
        return claims;
    }

}
