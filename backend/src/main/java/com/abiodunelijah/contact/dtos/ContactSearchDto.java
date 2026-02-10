package com.abiodunelijah.contact.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactSearchDto {

    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String state;
    private String country;
}
