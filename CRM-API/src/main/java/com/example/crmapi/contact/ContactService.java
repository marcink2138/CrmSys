package com.example.crmapi.contact;

import com.example.crmapi.contact.DTO.ContactDTO;
import com.example.crmapi.contact.DTO.ContactDTOMapper;
import com.example.crmapi.genericCrud.GenericService;
import com.example.crmapi.reflectionApi.ReflectionApiInvoker;
import com.example.crmapi.reflectionApi.TableUpdateDTO;
import com.example.crmapi.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ContactService extends GenericService<Contact, ContactDTO, ContactDTOMapper> {
    private final AuthenticationFacade authenticationFacade;
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, ReflectionApiInvoker invoker, AuthenticationFacade authenticationFacade, ContactDTOMapper contactDTOMapper) {
        super(contactRepository, invoker, contactDTOMapper);
        this.authenticationFacade = authenticationFacade;
        this.contactRepository = contactRepository;
    }

    public List<ContactDTO> getContactsCreatedByUser(Long userId) {
        return dtoMapper.mapToDTOs(contactRepository.findAllByCreatedBy(userId));
    }

    public List<ContactDTO> getContactsByUserGroup(Long groupId) {
        return dtoMapper.mapToDTOs(contactRepository.findAllByAccountGroupId(groupId));
    }

    @Override
    protected Contact getEntity(List<Contact> entities, TableUpdateDTO tableUpdateDTO) {
        return entities.stream()
                .filter(contact -> Objects.equals(contact.getId(), tableUpdateDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new ContactException("Contact with id : " + tableUpdateDTO.getId() + "not found!"));
    }

    @Override
    protected Contact buildEntity(ContactDTO dto) {
        return Contact.builder()
                .accountGroupId(authenticationFacade.getGroupId())
                .addressCity(dto.getAddressCity())
                .addressCountry(dto.getAddressCountry())
                .addressHouseNumber(dto.getAddressHouseNumber())
                .addressPostalCode(dto.getAddressPostalCode())
                .addressStreet(dto.getAddressStreet())
                .emailAddress(dto.getEmailAddress())
                .language(dto.getLanguage())
                .phone(dto.getPhone())
                .name(dto.getName())
                .surname(dto.getSurname())
                .creationDate(new Date(System.currentTimeMillis()))
                .createdBy(authenticationFacade.getUserId())
                .build();
    }

    public List<ContactDTO> getContactsByEmailGroup(Long emailGroupId) {
        List<Contact> contacts = contactRepository.findContactEmailsByEmailGroupId(emailGroupId);
        return dtoMapper.mapToDTOs(contacts);
    }
}
