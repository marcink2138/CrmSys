package com.example.crmapi.schemaInformation.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnDTO {
    private String columnName;
    private String columnType;
    private int columnSize;
    private String isNullable;
}
