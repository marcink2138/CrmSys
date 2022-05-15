package com.example.crmapi.user.DTO;

import com.example.crmapi.contact.DTO.ContactDTO;
import com.example.crmapi.user.User;

public class UserDTOMapper {
    private UserDTOMapper() {

    }

    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
}
