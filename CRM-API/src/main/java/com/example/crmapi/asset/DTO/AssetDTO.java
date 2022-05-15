package com.example.crmapi.asset.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AssetDTO {
    private Long id;
    private String orderedBy;
    private String soldBy;
    private Long amount;
    private Long productId;
}
