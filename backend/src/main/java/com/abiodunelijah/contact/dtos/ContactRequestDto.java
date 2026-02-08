package com.abiodunelijah.contact.dtos;

import com.abiodunelijah.contact.entities.Address;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ContactRequestDto {

    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private AddressRequestDto addressRequestDto;
}
