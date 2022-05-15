package com.example.crmapi.email;

import com.example.crmapi.email.entities.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailMessagesRepository extends JpaRepository<EmailMessage, Long> {
    @Query("SELECT DISTINCT e FROM EmailMessage e, Contact c WHERE e.contactEmail = c.emailAddress " +
            "AND c.emailAddress = ?1 OR e.emailGroupId " +
            "IN (SELECT eg.emailGroupId FROM ContactEmailGroup eg, Contact c1 WHERE eg.contactId = c1.id AND c1.emailAddress = ?1)" +
            "ORDER BY e.creationDate")
    List<EmailMessage> findAllByContactEmail(String emailAddress);

    List<EmailMessage> findAllByEmailGroupId(Long emailGroupId);

}
