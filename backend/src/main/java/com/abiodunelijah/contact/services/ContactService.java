package com.abiodunelijah.contact.services;


import com.abiodunelijah.contact.dtos.ContactRequestDto;

import java.util.List;

public interface ContactService {

    ContactRequestDto addContact(ContactRequestDto contactRequestDto);
    List<ContactRequestDto> getAllContacts();
    ContactRequestDto getContact(Integer contactId);
    ContactRequestDto updateContact(Integer contactId, ContactRequestDto contactRequestDto);
    void deleteContact(Integer contactId);
    List<ContactRequestDto> searchContacts(String firstName,String lastName, String phoneNumber);

}
