package com.example.crmapi.genericCrud;

import com.example.crmapi.reflectionApi.ReflectionApiInvoker;
import com.example.crmapi.reflectionApi.TableUpdateDTO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class GenericService<T extends BaseEntity, DTO, MAPPER extends GenericMapper<T, DTO>> {
    private final GenericRepository<T> genericRepository;
    private final ReflectionApiInvoker invoker;
    protected final MAPPER dtoMapper;

    public List<DTO> findAll() {
        return dtoMapper.mapToDTOs(genericRepository.findAll());
    }

    public DTO create(DTO dto) {
        T entity = buildEntity(dto);
        entity = genericRepository.save(entity);
        return dtoMapper.mapToDTO(entity);
    }

    public void update(List<TableUpdateDTO> tableUpdateDTOS) {
        List<Long> listOfIds = tableUpdateDTOS.stream().map(TableUpdateDTO::getId).collect(Collectors.toList());
        List<T> entities = genericRepository.findAllById(listOfIds);
        loopOverDTOSAndUpdate(tableUpdateDTOS, entities);
    }

    public void deleteAllByIds(List<Long> ids) {
        genericRepository.deleteAllById(ids);
    }

    private void loopOverDTOSAndUpdate(List<TableUpdateDTO> tableUpdateDTOS, List<T> entities) {
        tableUpdateDTOS.forEach(tableUpdateDTO -> {
            T entity = getEntity(entities, tableUpdateDTO);
            tableUpdateDTO.getColumnsValuesMap().forEach((k, v) -> {
                invoker.createSetterMethodName(k);
                invoker.runInvocation(entity, k, v);
            });
            genericRepository.save(entity);
        });
    }

    protected abstract T getEntity(List<T> entities, TableUpdateDTO tableUpdateDTO);

    protected abstract T buildEntity(DTO dto);

}
