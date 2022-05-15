package com.example.crmapi.email.entities;

import com.example.crmapi.email.EmailType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_messages")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EmailMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private String messageContent;
    @Enumerated(EnumType.STRING)
    private EmailType type;
    private String contactEmail;
    private LocalDateTime creationDate;
    private Long emailGroupId;
}
