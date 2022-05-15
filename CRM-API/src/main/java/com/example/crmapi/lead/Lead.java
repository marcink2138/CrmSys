package com.example.crmapi.lead;

import com.example.crmapi.genericCrud.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Table(name = "lead")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lead extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private LeadType leadType;
    @Email
    private String potentialEmail;
    private LocalDateTime creationDate;
}
