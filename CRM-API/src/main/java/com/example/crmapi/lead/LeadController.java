package com.example.crmapi.lead;

import com.example.crmapi.genericCrud.GenericCRUDController;
import com.example.crmapi.lead.DTO.LeadDTO;
import com.example.crmapi.lead.DTO.LeadDTOMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lead")
public class LeadController extends GenericCRUDController<Lead, LeadDTO, LeadDTOMapper> {
    public LeadController(LeadService leadService) {
        super(leadService);
    }
}
