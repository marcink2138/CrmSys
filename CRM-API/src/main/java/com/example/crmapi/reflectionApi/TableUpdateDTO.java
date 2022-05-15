package com.example.crmapi.reflectionApi;

import lombok.Data;

import java.util.Map;

@Data
public class TableUpdateDTO {
    Long id;
    Map<String, String> columnsValuesMap;
}
