package com.example.crmapi.lead.DTO;

import com.example.crmapi.genericCrud.GenericMapper;
import com.example.crmapi.lead.Lead;
import org.springframework.stereotype.Component;

@Component
public class LeadDTOMapper extends GenericMapper<Lead, LeadDTO> {
    @Override
    public LeadDTO mapToDTO(Lead entity) {
        return LeadDTO.builder()
                .creationDate(entity.getCreationDate())
                .leadType(entity.getLeadType())
                .id(entity.getId())
                .potentialEmail(entity.getPotentialEmail())
                .build();
    }

}
