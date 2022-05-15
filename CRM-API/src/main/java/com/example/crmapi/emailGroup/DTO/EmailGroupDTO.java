package com.example.crmapi.emailGroup.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmailGroupDTO {
    private Long id;
    private String emailGroupName;
    private Boolean forecastSub;
}
