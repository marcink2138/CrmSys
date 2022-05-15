package com.example.crmapi.lead;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class LeadException extends RuntimeException {
    public LeadException(String message) {
        super(message);
    }
}
