package com.abiodunelijah.contact.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddressRequestDto {

    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String state;
    private String country;
}
