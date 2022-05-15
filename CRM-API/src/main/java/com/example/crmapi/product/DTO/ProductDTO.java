package com.example.crmapi.product.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String otherOptions;
    private String otherOptions2;
}
