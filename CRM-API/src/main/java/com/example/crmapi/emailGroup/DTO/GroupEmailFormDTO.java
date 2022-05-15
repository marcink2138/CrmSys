package com.example.crmapi.emailGroup.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class GroupEmailFormDTO {
    private Long emailGroupId;
    private String topic;
    private String messageContent;
}
