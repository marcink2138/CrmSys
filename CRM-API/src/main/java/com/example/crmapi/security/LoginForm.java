package com.example.crmapi.security;

import lombok.Data;

@Data
public class LoginForm {
    private String email;
    private String password;
}
