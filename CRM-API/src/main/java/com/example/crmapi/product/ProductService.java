package com.example.crmapi.product;

import com.example.crmapi.genericCrud.GenericService;
import com.example.crmapi.product.DTO.ProductDTO;
import com.example.crmapi.product.DTO.ProductDTOMapper;
import com.example.crmapi.reflectionApi.ReflectionApiInvoker;
import com.example.crmapi.reflectionApi.TableUpdateDTO;
import com.example.crmapi.user.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends GenericService<Product, ProductDTO, ProductDTOMapper> {
    @Autowired
    public ProductService(ProductRepository productRepository, ReflectionApiInvoker invoker, ProductDTOMapper dtoMapper) {
        super(productRepository, invoker, dtoMapper);
    }

    @Override
    protected Product getEntity(List<Product> entities, TableUpdateDTO tableUpdateDTO) {
        return entities.stream().filter(entity -> entity.getId().equals(tableUpdateDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Product with id: " + tableUpdateDTO.getId() + " not found!"));
    }

    @Override
    protected Product buildEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .otherOptions(productDTO.getOtherOptions())
                .otherOptions2(productDTO.getOtherOptions2())
                .build();
    }

}
