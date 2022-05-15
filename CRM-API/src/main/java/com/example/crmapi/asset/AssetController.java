package com.example.crmapi.asset;

import com.example.crmapi.asset.DTO.AssetDTO;
import com.example.crmapi.asset.DTO.AssetDTOMapper;
import com.example.crmapi.genericCrud.GenericCRUDController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asset")
public class AssetController extends GenericCRUDController<Asset, AssetDTO, AssetDTOMapper> {

    public AssetController(AssetService assetService) {
        super(assetService);
    }
}


