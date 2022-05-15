package com.example.crmapi.lead;

import com.example.crmapi.genericCrud.GenericService;
import com.example.crmapi.lead.DTO.LeadDTO;
import com.example.crmapi.lead.DTO.LeadDTOMapper;
import com.example.crmapi.reflectionApi.ReflectionApiInvoker;
import com.example.crmapi.reflectionApi.TableUpdateDTO;
import com.example.crmapi.user.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeadService extends GenericService<Lead, LeadDTO, LeadDTOMapper> {

    @Autowired
    public LeadService(LeadRepository leadRepository, ReflectionApiInvoker invoker, LeadDTOMapper dtoMapper) {
        super(leadRepository, invoker, dtoMapper);
    }

    @Override
    protected Lead getEntity(List<Lead> entities, TableUpdateDTO tableUpdateDTO) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(tableUpdateDTO.getId()))
                .findFirst()
                .orElseThrow(()-> new NotFoundException("Lead with id: " + tableUpdateDTO.getId() + " not found!"));
    }

    @Override
    protected Lead buildEntity(LeadDTO leadDTO) {
        return Lead.builder()
                .creationDate(LocalDateTime.now())
                .leadType(leadDTO.getLeadType())
                .potentialEmail(leadDTO.getPotentialEmail())
                .build();
    }
}
