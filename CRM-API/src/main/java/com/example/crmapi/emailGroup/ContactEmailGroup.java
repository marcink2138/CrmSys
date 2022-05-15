package com.example.crmapi.emailGroup;

import com.example.crmapi.contact.Contact;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contact_email_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactEmailGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contactId;
    private Long emailGroupId;
    @ManyToOne
    @JoinColumn(name = "contactId", insertable = false, updatable = false)
    private Contact contact;

}
