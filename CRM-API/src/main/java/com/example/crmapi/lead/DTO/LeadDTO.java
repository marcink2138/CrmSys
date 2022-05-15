package com.example.crmapi.lead.DTO;

import com.example.crmapi.lead.LeadType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LeadDTO {
    private Long id;
    private LeadType leadType;
    private String potentialEmail;
    private LocalDateTime creationDate;
}
