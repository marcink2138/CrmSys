package com.example.crmapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfigHelper {
    @Bean
    BCryptPasswordEncoder getBCPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
