package com.example.crmapi.lead;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class LeadControllerAdvice {
    @ExceptionHandler(value = LeadException.class)
    public ResponseEntity<Object> handleLeadFromException(LeadException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.ALREADY_REPORTED);
    }
}
