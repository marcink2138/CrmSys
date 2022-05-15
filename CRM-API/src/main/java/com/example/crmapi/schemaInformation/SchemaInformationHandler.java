package com.example.crmapi.schemaInformation;

import com.example.crmapi.schemaInformation.DTO.TableDTO;
import com.example.crmapi.user.NotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SchemaInformationHandler {
    private final List<String> tableNameList;
    private final List<TableDTO> tableDTOList;

    @Autowired
    public SchemaInformationHandler(SchemaInformationService schemaInformationService) throws SQLException {
        tableNameList = schemaInformationService.getSchemaTables();
        tableDTOList = schemaInformationService.getTablesWithColumns();
    }

    public TableDTO getTableDTOByName(String tableName) {
        return tableDTOList.stream()
                .filter(tableDTO -> tableDTO.getTableName().equals(tableName))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Table " + tableName + " not found!"));
    }

}
