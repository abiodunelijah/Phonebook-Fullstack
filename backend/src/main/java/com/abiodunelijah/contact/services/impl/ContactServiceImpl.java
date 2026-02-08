package com.abiodunelijah.contact.services.impl;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.entities.Contact;
import com.abiodunelijah.contact.mappers.ContactMapper;
import com.abiodunelijah.contact.repositories.ContactRepository;
import com.abiodunelijah.contact.services.ContactService;
import com.abiodunelijah.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Override
    public ContactRequestDto addContact(ContactRequestDto contactRequestDto) {

        boolean existedContactByPhoneNumber = contactRepository.existsContactByPhoneNumber(contactRequestDto.getPhoneNumber());

        Contact contact = ContactMapper.mapToDto(contactRequestDto);

        return ContactMapper.mapToEntity(contact);
    }
}
