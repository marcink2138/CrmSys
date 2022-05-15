package com.example.crmapi.genericCrud;

import com.example.crmapi.reflectionApi.TableUpdateDTO;
import com.example.crmapi.security.AuthenticationFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericCRUDController<T extends BaseEntity, DTO, MAPPER extends GenericMapper<T, DTO>> {
    private final GenericService<T, DTO, MAPPER> genericService;

    public GenericCRUDController(GenericService<T, DTO, MAPPER> genericService) {
        this.genericService = genericService;
    }

    @PostMapping("/create")
    public DTO create(@RequestBody DTO dto) {
        return genericService.create(dto);
    }

    @GetMapping("/read")
    public List<DTO> readContacts() {
        return genericService.findAll();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateContact(@RequestBody List<TableUpdateDTO> tableUpdateDTOS) {
        genericService.update(tableUpdateDTOS);
        Map<String, String> map = new HashMap<>();
        map.put("message", (long) tableUpdateDTOS.size() + " rows updated successful!");
        return ResponseEntity.ok(map);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteContact(@RequestBody List<Long> idsMap) {
        genericService.deleteAllByIds(idsMap);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Rows deleted successful!");
        return ResponseEntity.ok(map);
    }


}
