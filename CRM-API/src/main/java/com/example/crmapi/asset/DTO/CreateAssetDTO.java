package com.example.crmapi.asset.DTO;

import lombok.Data;

@Data
public class CreateAssetDTO {
    private Long orderedBy;
    private Long soldBy;
    private Long amount;
    private Long productId;
}
