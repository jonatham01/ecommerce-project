package com.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisteredUser {
    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;

}
