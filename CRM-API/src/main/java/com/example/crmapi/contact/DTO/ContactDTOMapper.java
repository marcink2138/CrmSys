package com.example.crmapi.contact.DTO;

import com.example.crmapi.contact.Contact;
import com.example.crmapi.genericCrud.GenericMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactDTOMapper extends GenericMapper<Contact, ContactDTO> {
    @Override
    public ContactDTO mapToDTO(Contact entity) {
        return ContactDTO.builder()
                .id(entity.getId())
                .addressCity(entity.getAddressCity())
                .addressCountry(entity.getAddressCountry())
                .addressHouseNumber(entity.getAddressHouseNumber())
                .addressPostalCode(entity.getAddressPostalCode())
                .addressStreet(entity.getAddressStreet())
                .emailAddress(entity.getEmailAddress())
                .language(entity.getLanguage())
                .name(entity.getName())
                .surname(entity.getSurname())
                .phone(entity.getPhone())
                .build();
    }
}
