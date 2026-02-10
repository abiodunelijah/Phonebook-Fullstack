package com.abiodunelijah.contact.services;


import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.dtos.ContactSearchDto;

import java.util.List;

public interface ContactService {

    ContactRequestDto addContact(ContactRequestDto contactRequestDto);
    List<ContactRequestDto> getAllContacts();
    ContactRequestDto getContact(Integer contactId);
    ContactRequestDto updateContact(Integer contactId, ContactRequestDto contactRequestDto);
    void deleteContact(Integer contactId);
    List<ContactSearchDto> searchContacts(ContactSearchDto searchDto);

}
