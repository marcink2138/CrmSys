package com.example.crmapi.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;
    private Date licenseEnd;
    private String login;
    private String surname;
    private Long phone;
    private String name;
    private Long groupId;

    public User(UserRegisterForm registerForm){
        this.email = registerForm.getEmail();
        this.login = registerForm.getEmail();
        this.password = registerForm.getPassword();
        this.role = "SIEMA";
        this.licenseEnd = new Date(System.currentTimeMillis());
        this.name = registerForm.getName();
        this.surname = registerForm.getSurname();
        this.phone = registerForm.getPhone();
    }
}
