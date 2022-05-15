package com.example.crmapi.asset;

import com.example.crmapi.asset.DTO.AssetDTO;
import com.example.crmapi.asset.DTO.AssetDTOMapper;
import com.example.crmapi.contact.Contact;
import com.example.crmapi.contact.ContactException;
import com.example.crmapi.contact.ContactRepository;
import com.example.crmapi.genericCrud.GenericService;
import com.example.crmapi.reflectionApi.ReflectionApiInvoker;
import com.example.crmapi.reflectionApi.TableUpdateDTO;
import com.example.crmapi.user.NotFoundException;
import com.example.crmapi.user.User;
import com.example.crmapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AssetService extends GenericService<Asset, AssetDTO, AssetDTOMapper> {
    private final AssetRepository assetRepository;
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository, ReflectionApiInvoker invoker, ContactRepository contactRepository, UserRepository userRepository, AssetDTOMapper assetDTOMapper) {
        super(assetRepository, invoker, assetDTOMapper);
        this.assetRepository = assetRepository;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void update(List<TableUpdateDTO> tableUpdateDTOS) {
        tableUpdateDTOS.forEach(tableUpdateDTO -> {
            if (tableUpdateDTO.getColumnsValuesMap().containsKey("orderedBy")) {
                Long contactId = contactRepository.getByEmailAddress(tableUpdateDTO.getColumnsValuesMap().get("orderedBy")).getId();
                tableUpdateDTO.getColumnsValuesMap().put("orderedBy", contactId.toString());
            }
            if (tableUpdateDTO.getColumnsValuesMap().containsKey("soldBy")) {
                String userEmail = tableUpdateDTO.getColumnsValuesMap().get("soldBy");
                Long userId = userRepository.findUserByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("Cannot find user: " + userEmail)).getId();
                tableUpdateDTO.getColumnsValuesMap().put("soldBy", userId.toString());
            }
        });
        super.update(tableUpdateDTOS);
    }

    @Override
    @Transactional
    public AssetDTO create(AssetDTO assetDTO) {
        Contact contact = contactRepository.getByEmailAddress(assetDTO.getOrderedBy());
        if (contact == null)
            throw new ContactException("Contact: " + assetDTO.getOrderedBy() + " not found!");
        else {
            Optional<User> user = userRepository.findUserByEmail(assetDTO.getSoldBy());
            if (user.isPresent()) {
                Asset asset = buildCustomAsset(assetDTO, contact.getId(), user.get().getId());
                Asset asset1 = assetRepository.save(asset);
                asset1.setContact(contact);
                asset1.setUser(user.get());
                return dtoMapper.mapToDTO(asset1);
            }
            throw new NotFoundException("User: " + assetDTO.getSoldBy() + " not found!");
        }
    }


    @Override
    protected Asset getEntity(List<Asset> entities, TableUpdateDTO tableUpdateDTO) {
        return entities.stream()
                .filter(entity -> Objects.equals(entity.getId(), tableUpdateDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new ContactException("Contact with id : " + tableUpdateDTO.getId() + "not found!"));
    }

    @Override
    protected Asset buildEntity(AssetDTO assetDTO) {
        return Asset.builder()
                .amount(assetDTO.getAmount())
                .productId(assetDTO.getProductId())
                .build();
    }


    private Asset buildCustomAsset(AssetDTO assetDTO, Long contactId, Long userId) {
        return Asset.builder()
                .amount(assetDTO.getAmount())
                .orderedBy(contactId)
                .soldBy(userId)
                .productId(assetDTO.getProductId())
                .build();
    }


}
