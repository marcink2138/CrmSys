package com.example.crmapi.product;

import com.example.crmapi.genericCrud.GenericCRUDController;
import com.example.crmapi.product.DTO.ProductDTO;
import com.example.crmapi.product.DTO.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product")
public class ProductController extends GenericCRUDController<Product, ProductDTO, ProductDTOMapper> {
    @Autowired
    public ProductController(ProductService productService) {
        super(productService);
    }
}
