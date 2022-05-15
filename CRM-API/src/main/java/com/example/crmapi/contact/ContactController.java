package com.example.crmapi.contact;

import com.example.crmapi.contact.DTO.ContactDTO;
import com.example.crmapi.contact.DTO.ContactDTOMapper;
import com.example.crmapi.genericCrud.GenericCRUDController;
import com.example.crmapi.genericCrud.GenericService;
import com.example.crmapi.reflectionApi.TableUpdateDTO;
import com.example.crmapi.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/contact")
public class ContactController extends GenericCRUDController<Contact, ContactDTO, ContactDTOMapper> {
    private final ContactService contactService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public ContactController(ContactService contactService, AuthenticationFacade authenticationFacade) {
        super(contactService);
        this.contactService = contactService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/created-by")
    public List<ContactDTO> getContactsCreatedByUser() {
        return contactService.getContactsCreatedByUser(authenticationFacade.getUserId());
    }

    @GetMapping("/created-by/{id}")
    public List<ContactDTO> getContactsCreatedByUser(@PathVariable("id") Long userId) {
        return contactService.getContactsCreatedByUser(userId);
    }

    @GetMapping("/group-contacts")
    public List<ContactDTO> getGroupContacts() {
        return contactService.getContactsByUserGroup(authenticationFacade.getGroupId());
    }

    @GetMapping("/group-contacts/{id}")
    public List<ContactDTO> getGroupContacts(@PathVariable("id") Long groupId) {
        return contactService.getContactsCreatedByUser(groupId);
    }
    @GetMapping("/get-by-email-group/{id}")
    public List<ContactDTO> getContactsByEmailGroup(@PathVariable("id") Long emailGroupId) {
        return contactService.getContactsByEmailGroup(emailGroupId);
    }
}
