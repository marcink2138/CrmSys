package com.example.crmapi.email;

import com.example.crmapi.contact.Contact;
import com.example.crmapi.email.DTO.EmailFormDTO;
import com.example.crmapi.emailGroup.DTO.GroupEmailFormDTO;
import com.example.crmapi.email.entities.EmailMessage;
import com.example.crmapi.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class EmailController {
    private final AuthenticationFacade authenticationFacade;
    private final EmailService emailService;

    @GetMapping("/messages/{emailAddress}")
    public List<EmailMessage> getContactEmails(@PathVariable @Email String emailAddress) {
        return emailService.getEmailMessages(emailAddress);
    }

    @GetMapping("/chat-rooms")
    public List<Contact> getChatRooms() {
        return emailService.getRelatedContactsEmails(authenticationFacade.getUserId(), authenticationFacade.getGroupId());
    }

    @PostMapping("/client/send")
    public ResponseEntity<?> sendEmailClient(@Valid @RequestBody EmailFormDTO emailFormDTO) {
        Map<String, String> map = emailService.saveEmailFromClient(emailFormDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/user/send")
    public ResponseEntity<?> sendEmailUser(@RequestBody @Valid EmailFormDTO emailFormDTO) {
        Map<String, String> map = emailService.sendEmailFromUser(emailFormDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/user/send/group")
    public ResponseEntity<?> sendGroupEmail(@RequestBody GroupEmailFormDTO groupEmailFormDTO) {
        emailService.sendGroupEmail(groupEmailFormDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create/email-group/{emailGroupName}")
    public ResponseEntity<?> createEmailGroup(@PathVariable String emailGroupName) {
        String emailGroup = emailService.createEmailGroup(emailGroupName, authenticationFacade.getUserId());
        Map<String, String> map = new HashMap<>();
        map.put("message", "Email group " + emailGroup + " created successful!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/email-group-messages/{emailGroupId}")
    public List<EmailMessage> getEmailGroupMessages(@PathVariable Long emailGroupId) {
        return emailService.getGroupEmailMessages(emailGroupId);
    }

}
