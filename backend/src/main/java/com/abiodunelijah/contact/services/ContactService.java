package com.abiodunelijah.contact.services;


import com.abiodunelijah.contact.dtos.ContactRequestDto;

import java.util.List;

public interface ContactService {

    ContactRequestDto addContact(ContactRequestDto contactRequestDto );
    List<ContactRequestDto> getAllContacts();
}
