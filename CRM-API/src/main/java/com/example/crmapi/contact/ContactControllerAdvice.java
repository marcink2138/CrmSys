package com.example.crmapi.contact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ContactControllerAdvice {
    @ExceptionHandler(value = ContactException.class)
    public ResponseEntity<Object> handleDuplicatedKeyException(ContactException e){
        Map<String, String> data = new HashMap<>();
        data.put("message", e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
