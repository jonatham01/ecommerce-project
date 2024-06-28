package com.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveUser {

    @Size(min = 6, max = 20)
    private String username;
    @Size(min = 6, max = 20)
    private String name;
    @Size(min = 6, max = 20)
    private String password;
    @Size(min = 6, max = 20)
    private String repeatedPassword;
}
