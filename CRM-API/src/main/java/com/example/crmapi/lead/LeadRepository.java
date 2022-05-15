package com.example.crmapi.lead;

import com.example.crmapi.genericCrud.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends GenericRepository<Lead> {
    Lead findByPotentialEmail(String email);
}
