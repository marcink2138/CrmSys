package com.example.crmapi.schemaInformation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@RequiredArgsConstructor
public class SchemaInformationConfig {
    private final SchemaInformationService schemaInformationService;

    @Bean
    public SchemaInformationHandler getSchemaInformationHandler() throws SQLException {
        return new SchemaInformationHandler(schemaInformationService);
    }
}
