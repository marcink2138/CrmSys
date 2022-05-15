package com.example.crmapi.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableAsync
@EnableScheduling
public class EmailConfig {

    @Bean
    public DateTimeFormatter getDateTimeFormatter(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

}
