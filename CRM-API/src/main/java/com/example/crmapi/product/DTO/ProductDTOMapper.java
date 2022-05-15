package com.example.crmapi.product.DTO;

import com.example.crmapi.genericCrud.GenericMapper;
import com.example.crmapi.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOMapper extends GenericMapper<Product, ProductDTO> {
    @Override
    public ProductDTO mapToDTO(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .otherOptions(entity.getOtherOptions())
                .otherOptions2(entity.getOtherOptions2())
                .build();
    }

}
