package com.example.crmapi.user;

import lombok.Data;

@Data
class UserRegisterForm {
    private String email;
    private String password;
    private String name;
    private String surname;
    private Long phone;
}
