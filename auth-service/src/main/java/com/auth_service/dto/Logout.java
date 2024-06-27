package com.auth_service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Logout implements Serializable {

    private String message;

    public Logout(String message) {
        this.message = message;
    }
}
