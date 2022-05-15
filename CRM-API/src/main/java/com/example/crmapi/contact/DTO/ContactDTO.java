package com.example.crmapi.contact.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDTO {
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

}
