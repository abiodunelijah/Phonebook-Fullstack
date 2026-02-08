package com.abiodunelijah.contact.mappers;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.entities.Address;
import com.abiodunelijah.contact.entities.Contact;

public class ContactMapper {

    public static ContactRequestDto mapToEntity(Contact contact){

        return ContactRequestDto.builder()
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phoneNumber(contact.getPhoneNumber())
                .addressRequestDto()
                .build();
    }

    public static Contact mapToDto(ContactRequestDto contactRequestDto){

        return Contact.builder()
                .firstName(contactRequestDto.getFirstName())
                .lastName(contactRequestDto.getLastName())
                .phoneNumber(contactRequestDto.getPhoneNumber())
                .address()
                .build();
    }

}
