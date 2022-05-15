package com.example.crmapi.genericCrud;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericMapper <T, DTO>{
    public abstract DTO mapToDTO(T entity);
    public  List<DTO> mapToDTOs(List<T> entities){
        return entities.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
