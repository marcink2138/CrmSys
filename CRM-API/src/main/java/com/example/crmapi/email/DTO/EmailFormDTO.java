package com.example.crmapi.email.DTO;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder
public class EmailFormDTO {
    private String topic;
    private String messageContent;
    @Email
    private String email;
}
