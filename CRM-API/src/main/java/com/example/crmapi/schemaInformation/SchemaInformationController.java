package com.example.crmapi.schemaInformation;

import com.example.crmapi.schemaInformation.DTO.ColumnDTO;
import com.example.crmapi.schemaInformation.DTO.TableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/db-info")
@RequiredArgsConstructor
public class SchemaInformationController {
    private final SchemaInformationHandler schemaInformationHandler;

    @GetMapping("/table-names")
    public List<String> getTableNames() {
        return schemaInformationHandler.getTableNameList();
    }

    @GetMapping("/table-columns/{table}")
    public List<ColumnDTO> getTableColumns(@PathVariable String table) {
        return schemaInformationHandler.getTableDTOByName(table).getColumns();
    }

    @GetMapping("/table-columns")
    public List<TableDTO> getTableColumns() {
        return schemaInformationHandler.getTableDTOList();
    }

}
