package com.example.crmapi.emailGroup.DTO;

import com.example.crmapi.emailGroup.EmailGroup;
import com.example.crmapi.genericCrud.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class EmailGroupDTOMapper extends GenericMapper<EmailGroup, EmailGroupDTO> {
    @Override
    public EmailGroupDTO mapToDTO(EmailGroup entity) {
        return EmailGroupDTO.builder()
                .emailGroupName(entity.getEmailGroupName())
                .id(entity.getId())
                .forecastSub(entity.getForecastSub())
                .build();
    }
}
