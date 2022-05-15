package com.example.crmapi.emailGroup.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddOrDeleteFromEmailGroupDTO {
    private Long emailGroupId;
    private List<String> contactsEmails;
}
