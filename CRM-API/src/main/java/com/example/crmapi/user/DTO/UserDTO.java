package com.example.crmapi.user.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDTO {
    private String email;
    private Long phone;
    private String name;
}
