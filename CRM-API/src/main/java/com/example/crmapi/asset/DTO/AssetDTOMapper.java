package com.example.crmapi.asset.DTO;

import com.example.crmapi.asset.Asset;
import com.example.crmapi.genericCrud.GenericMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssetDTOMapper extends GenericMapper<Asset, AssetDTO> {
    private AssetDTOMapper() {

    }

    @Override
    public AssetDTO mapToDTO(Asset entity) {
        return AssetDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .orderedBy(entity.getContact().getEmailAddress())
                .productId(entity.getProductId())
                .soldBy(entity.getUser().getEmail())
                .build();
    }
}
