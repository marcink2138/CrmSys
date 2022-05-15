package com.example.crmapi.product;

import com.example.crmapi.genericCrud.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends GenericRepository<Product> {
}
