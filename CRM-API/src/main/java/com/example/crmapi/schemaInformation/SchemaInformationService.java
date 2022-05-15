package com.example.crmapi.schemaInformation;

import com.example.crmapi.schemaInformation.DTO.ColumnDTO;
import com.example.crmapi.schemaInformation.DTO.TableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SchemaInformationService {
    private final DataSource dataSource;

    public List<String> getSchemaTables() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables("crmsys", "crmsys", null, new String[]{"TABLE"});
        List<String> listOfNames = new ArrayList<>();
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            listOfNames.add(tableName);
        }
        connection.close();
        return listOfNames;
    }

    @Deprecated
    public List<ColumnDTO> getTableColumns(String tableName) throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        List<ColumnDTO> columnDTOS = getColumnDTOS(tableName, metaData);
        connection.close();
        return columnDTOS;
    }

    public List<TableDTO> getTablesWithColumns() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables("crmsys", "crmsys", null, new String[]{"TABLE"});
        List<TableDTO> listOfTables = new ArrayList<>();
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            listOfTables.add(new TableDTO(tableName, getColumnDTOS(tableName, metaData)));
        }

        connection.close();
        return listOfTables;
    }

    private List<ColumnDTO> getColumnDTOS(String tableName, DatabaseMetaData metaData) throws SQLException {
        List<ColumnDTO> listOfColumns = new ArrayList<>();
        ResultSet columns = metaData.getColumns("crmsys", "crmsys", tableName, "%");
        while (columns.next()) {
            if (!columns.getString("COLUMN_NAME").equals("id")) {
                ColumnDTO columnDTO = columnDTOBuilder(columns);
                listOfColumns.add(columnDTO);
            }
        }
        return listOfColumns;
    }

    private ColumnDTO columnDTOBuilder(ResultSet rs) throws SQLException {
        return ColumnDTO.builder()
                .columnName(rs.getString("COLUMN_NAME"))
                .columnSize(rs.getInt("COLUMN_SIZE"))
                .columnType(rs.getString("TYPE_NAME"))
                .isNullable(rs.getString("IS_NULLABLE"))
                .build();
    }

}
