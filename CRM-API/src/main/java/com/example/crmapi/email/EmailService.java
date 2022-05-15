package com.example.crmapi.email;

import com.example.crmapi.contact.Contact;
import com.example.crmapi.contact.ContactRepository;
import com.example.crmapi.email.DTO.EmailFormDTO;
import com.example.crmapi.emailGroup.DTO.EmailGroupDTO;
import com.example.crmapi.emailGroup.DTO.GroupEmailFormDTO;
import com.example.crmapi.emailGroup.EmailGroup;
import com.example.crmapi.email.entities.EmailMessage;
import com.example.crmapi.emailGroup.EmailGroupRepository;
import com.example.crmapi.lead.LeadType;
import com.example.crmapi.lead.Lead;
import com.example.crmapi.lead.LeadException;
import com.example.crmapi.lead.LeadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.mail.Message;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final ContactRepository contactRepository;
    private final EmailMessagesRepository emailMessagesRepository;
    private final LeadRepository leadRepository;
    private final EmailSenderService emailSenderService;
    private final EmailGroupRepository emailGroupRepository;

    public List<Contact> getRelatedContactsEmails(Long userId, Long groupId) {
        return contactRepository.findContactEmailsByCreatedByAndGroupId(userId, groupId);
    }

    public List<EmailMessage> getEmailMessages(String emailAddress) {
        return emailMessagesRepository.findAllByContactEmail(emailAddress);
    }

    public String createEmailGroup(String emailGroupName, Long userId) {
        EmailGroup emailGroup = new EmailGroup(emailGroupName, userId);
        emailGroupRepository.save(emailGroup);
        return emailGroupName;
    }

    public List<EmailGroupDTO> emailGroupsCreatedByUser(Long userId) {
        List<EmailGroup> emailGroups = emailGroupRepository.findEmailGroupByUserId(userId);
        return emailGroups.stream()
                .map(emailGroup -> new EmailGroupDTO(emailGroup.getId(), emailGroup.getEmailGroupName(), emailGroup.getForecastSub()))
                .collect(Collectors.toList());
    }

    public List<EmailMessage> getGroupEmailMessages(Long emailGroupId) {
        return emailMessagesRepository.findAllByEmailGroupId(emailGroupId);
    }

    @Transactional
    public void sendGroupEmail(GroupEmailFormDTO groupEmailFormDTO) {
        Optional<EmailGroup> emailGroup = emailGroupRepository.findById(groupEmailFormDTO.getEmailGroupId());
        if (emailGroup.isPresent()) {
            EmailMessage emailMessage = buildGroupEmailMessage(groupEmailFormDTO);
            emailMessagesRepository.save(emailMessage);
            List<Contact> contacts = contactRepository.findContactEmailsByEmailGroupId(groupEmailFormDTO.getEmailGroupId());
            String[] emails = contacts.stream().map(Contact::getEmailAddress).toArray(String[]::new);
            EmailFormDTO emailFormDTO = buildEmailFormDTO(groupEmailFormDTO);
            emailSenderService.sendBulkEmail(emailFormDTO, emails);
        } else {
            throw new RuntimeException("Cannot find group " + groupEmailFormDTO.getEmailGroupId());
        }
    }

    @Transactional
    public Map<String, String> saveEmailFromClient(EmailFormDTO emailFormDTO) {
        Contact contact = contactRepository.findByEmailAddress(emailFormDTO.getEmail());
        if (contact == null) {
            Lead lead = leadRepository.findByPotentialEmail(emailFormDTO.getEmail());
            if (lead == null) {
                Lead leadToInsert = buildLeadFrom(emailFormDTO.getEmail());
                leadRepository.save(leadToInsert);
                return getResponseBody("message", "Thank you for contact. We are already processing your request.");
            } else {
                throw new LeadException("We are already processing your request. Thank you for your patience!");
            }
        } else {
            EmailMessage emailMessage = buildEmailMessage(emailFormDTO, EmailType.SEND_BY_CONTACT);
            emailMessagesRepository.save(emailMessage);
            return getResponseBody("message", "Thank you for contact. Our employee will contact with you as fast as possible");
        }
    }

    @Transactional
    public Map<String, String> sendEmailFromUser(EmailFormDTO emailFormDTO) {
        Contact contact = contactRepository.findByEmailAddress(emailFormDTO.getEmail());
        if (contact != null) {
            saveEmailFromUser(emailFormDTO);
            emailSenderService.sendEmail(emailFormDTO);
            return getResponseBody("message", "Email sent!");
        } else {
            throw new LeadException("There is no contact related to this email address!");
        }

    }

    @Scheduled(cron = "0 30 5 * * 1", zone = "Europe/Warsaw")
    public void sendForecastEmails() {
        List<Contact> contacts = contactRepository.findContactsWithForecastSubscription();
        emailSenderService.sendForecastEmails(contacts);
    }

    private EmailMessage saveEmailFromUser(EmailFormDTO emailFormDTO) {
        EmailMessage emailMessage = buildEmailMessage(emailFormDTO, EmailType.SEND_BY_USER);
        return emailMessagesRepository.save(emailMessage);
    }

    private Map<String, String> getResponseBody(String a, String b) {
        Map<String, String> map = new HashMap<>();
        map.put(a, b);
        return map;
    }

    private EmailMessage buildEmailMessage(EmailFormDTO emailFormDTO, EmailType emailType) {
        return EmailMessage.builder()
                .contactEmail(emailFormDTO.getEmail())
                .creationDate(LocalDateTime.now())
                .messageContent(emailFormDTO.getMessageContent())
                .topic(emailFormDTO.getTopic())
                .type(emailType)
                .build();
    }

    private EmailMessage buildGroupEmailMessage(GroupEmailFormDTO groupEmailFormDTO) {
        return EmailMessage.builder()
                .creationDate(LocalDateTime.now())
                .messageContent(groupEmailFormDTO.getMessageContent())
                .topic(groupEmailFormDTO.getTopic())
                .emailGroupId(groupEmailFormDTO.getEmailGroupId())
                .type(EmailType.GROUP_EMAIL)
                .contactEmail("")
                .build();
    }

    private EmailFormDTO buildEmailFormDTO(GroupEmailFormDTO groupEmailFormDTO) {
        return EmailFormDTO.builder()
                .messageContent(groupEmailFormDTO.getMessageContent())
                .topic(groupEmailFormDTO.getTopic())
                .build();
    }

    private Lead buildLeadFrom(String email) {
        return Lead.builder()
                .potentialEmail(email)
                .creationDate(LocalDateTime.now())
                .leadType(LeadType.APP_FORM)
                .build();
    }

}
