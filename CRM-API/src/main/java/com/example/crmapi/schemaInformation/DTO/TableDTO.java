package com.example.crmapi.schemaInformation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TableDTO {
    private String tableName;
    private List<ColumnDTO> columns;
}
