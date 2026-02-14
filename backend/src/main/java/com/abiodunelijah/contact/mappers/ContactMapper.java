package com.abiodunelijah.contact.mappers;

import com.abiodunelijah.contact.dtos.AddressRequestDto;
import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.entities.Address;
import com.abiodunelijah.contact.entities.Contact;

public class ContactMapper {

    public static ContactRequestDto mapToDto(Contact contact) {
        AddressRequestDto addressRequestDto = null;

        if (contact.getAddress() != null) {
            addressRequestDto = new AddressRequestDto();
            addressRequestDto.setStreetNumber(contact.getAddress().getStreetNumber());
            addressRequestDto.setStreetName(contact.getAddress().getStreetName());
            addressRequestDto.setPostalCode(contact.getAddress().getPostalCode());
            addressRequestDto.setState(contact.getAddress().getState());
            addressRequestDto.setCountry(contact.getAddress().getCountry());
        }

        return ContactRequestDto.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phoneNumber(contact.getPhoneNumber())
                .addressRequestDto(addressRequestDto)
                .build();
    }

    public static Contact mapToEntity(ContactRequestDto contactRequestDto) {
        Address address = null;

        if (contactRequestDto.getAddressRequestDto() != null) {
            address = Address.builder()
                    .streetNumber(contactRequestDto.getAddressRequestDto().getStreetNumber())
                    .streetName(contactRequestDto.getAddressRequestDto().getStreetName())
                    .postalCode(contactRequestDto.getAddressRequestDto().getPostalCode())
                    .state(contactRequestDto.getAddressRequestDto().getState())
                    .country(contactRequestDto.getAddressRequestDto().getCountry())
                    .build();
        }

        return Contact.builder()
                .id(contactRequestDto.getId())
                .firstName(contactRequestDto.getFirstName())
                .lastName(contactRequestDto.getLastName())
                .phoneNumber(contactRequestDto.getPhoneNumber())
                .address(address)
                .build();
    }
}