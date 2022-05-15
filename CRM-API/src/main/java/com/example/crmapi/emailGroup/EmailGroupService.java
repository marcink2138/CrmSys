package com.example.crmapi.emailGroup;

import com.example.crmapi.contact.Contact;
import com.example.crmapi.contact.ContactException;
import com.example.crmapi.contact.ContactRepository;
import com.example.crmapi.emailGroup.DTO.AddOrDeleteFromEmailGroupDTO;
import com.example.crmapi.emailGroup.DTO.EmailGroupDTO;
import com.example.crmapi.emailGroup.DTO.EmailGroupDTOMapper;
import com.example.crmapi.genericCrud.GenericService;
import com.example.crmapi.reflectionApi.ReflectionApiInvoker;
import com.example.crmapi.reflectionApi.TableUpdateDTO;
import com.example.crmapi.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmailGroupService extends GenericService<EmailGroup, EmailGroupDTO, EmailGroupDTOMapper> {
    private final AuthenticationFacade authenticationFacade;
    private final EmailGroupRepository emailGroupRepository;
    private final ContactRepository contactRepository;
    private final ContactEmailGroupRepository contactEmailGroupRepository;

    @Autowired
    public EmailGroupService(EmailGroupRepository emailGroupRepository, ReflectionApiInvoker invoker, EmailGroupDTOMapper dtoMapper, AuthenticationFacade authenticationFacade, ContactRepository contactRepository, ContactEmailGroupRepository contactEmailGroupRepository) {
        super(emailGroupRepository, invoker, dtoMapper);
        this.authenticationFacade = authenticationFacade;
        this.emailGroupRepository = emailGroupRepository;
        this.contactRepository = contactRepository;
        this.contactEmailGroupRepository = contactEmailGroupRepository;
    }

    @Override
    public List<EmailGroupDTO> findAll() {
        return dtoMapper.mapToDTOs(emailGroupRepository.findEmailGroupByUserId(authenticationFacade.getUserId()));
    }

    @Transactional
    public void addUsersToEmailGroup(AddOrDeleteFromEmailGroupDTO addToEmailGroupDTO) {
        List<Long> contactIds = getContactIds(addToEmailGroupDTO);
        Long emailGroupId = addToEmailGroupDTO.getEmailGroupId();
        List<ContactEmailGroup> contactEmailGroups = new LinkedList<>();
        contactIds.forEach(contactId -> contactEmailGroups.add(buildContactEmailGroup(emailGroupId, contactId)));
        contactEmailGroupRepository.saveAll(contactEmailGroups);
    }

    @Transactional
    public void deleteUsersFromEmailGroup(AddOrDeleteFromEmailGroupDTO deleteFromEmailGroupDTO) {
        List<Long> contactIds = getContactIds(deleteFromEmailGroupDTO);
        Long emailGroupId = deleteFromEmailGroupDTO.getEmailGroupId();
        contactEmailGroupRepository.deleteAllByContactIdAndEmailGroupId(contactIds, emailGroupId);
    }

    @Transactional
    public void updateGroupsForecastSub(List<String> groups, Boolean addOrDelete) {
        emailGroupRepository.setGroupsForecastSub(groups, addOrDelete);
    }

    private void sendForecastEmails(){
        List<Contact> contacts = contactRepository.findContactsWithForecastSubscription();

    }

    private List<Long> getContactIds(AddOrDeleteFromEmailGroupDTO addToEmailGroupDTO) {
        List<String> emails = addToEmailGroupDTO.getContactsEmails();
        return contactRepository.findAllByEmailAddresses(emails)
                .stream().map(Contact::getId)
                .collect(Collectors.toList());
    }

    @Override
    protected EmailGroup getEntity(List<EmailGroup> entities, TableUpdateDTO tableUpdateDTO) {
        return entities.stream()
                .filter(entity -> Objects.equals(entity.getId(), tableUpdateDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new ContactException("Contact with id : " + tableUpdateDTO.getId() + "not found!"));
    }

    @Override
    protected EmailGroup buildEntity(EmailGroupDTO emailGroupDTO) {
        return EmailGroup.builder()
                .emailGroupName(emailGroupDTO.getEmailGroupName())
                .userId(authenticationFacade.getUserId())
                .forecastSub(false)
                .build();
    }

    private ContactEmailGroup buildContactEmailGroup(Long emailGroupId, Long contactId) {
        return ContactEmailGroup.builder()
                .contactId(contactId)
                .emailGroupId(emailGroupId)
                .build();
    }

}
