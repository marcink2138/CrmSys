package com.example.crmapi.contact;

import com.example.crmapi.genericCrud.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private Long phone;
    private String emailAddress;
    private String language;
    private String addressCity;
    private String addressCountry;
    private String addressPostalCode;
    private String addressStreet;
    private String addressHouseNumber;
    private Long accountGroupId;
    private Date creationDate;
    private Long createdBy;

}
