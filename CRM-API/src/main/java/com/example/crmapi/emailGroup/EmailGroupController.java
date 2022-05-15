package com.example.crmapi.emailGroup;

import com.example.crmapi.emailGroup.DTO.AddOrDeleteFromEmailGroupDTO;
import com.example.crmapi.emailGroup.DTO.EmailGroupDTO;
import com.example.crmapi.emailGroup.DTO.EmailGroupDTOMapper;
import com.example.crmapi.genericCrud.GenericCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/EmailGroup")
public class EmailGroupController extends GenericCRUDController<EmailGroup, EmailGroupDTO, EmailGroupDTOMapper> {
    private final EmailGroupService emailGroupService;

    @Autowired
    public EmailGroupController(EmailGroupService emailGroupService) {
        super(emailGroupService);
        this.emailGroupService = emailGroupService;
    }

    @PostMapping("/add-contact")
    public ResponseEntity<?> addUsersToEmailGroup(@RequestBody AddOrDeleteFromEmailGroupDTO addToEmailGroupDTO) {
        emailGroupService.addUsersToEmailGroup(addToEmailGroupDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete-contact")
    public ResponseEntity<?> deleteUsersFromEmailGroup(@RequestBody AddOrDeleteFromEmailGroupDTO deleteFromEmailGroupDTO) {
        emailGroupService.deleteUsersFromEmailGroup(deleteFromEmailGroupDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/forecast/{action}")
    public ResponseEntity<?> updateGroupsForecastSub(@RequestBody List<String> groups, @PathVariable String action) {
        Boolean addOrDelete = action.equals("add");
        emailGroupService.updateGroupsForecastSub(groups, addOrDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
