package com.abiodunelijah.contact.services.impl;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.entities.Contact;
import com.abiodunelijah.contact.mappers.ContactMapper;
import com.abiodunelijah.contact.repositories.ContactRepository;
import com.abiodunelijah.contact.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public ContactRequestDto addContact(ContactRequestDto contactRequestDto) {
        boolean existedContactByPhoneNumber = contactRepository
                .existsContactByPhoneNumber(contactRequestDto.getPhoneNumber());

        if (existedContactByPhoneNumber) {
            throw new RuntimeException("Contact with phone number " +
                    contactRequestDto.getPhoneNumber() + " already exists");
        }

        Contact contact = ContactMapper.mapToEntity(contactRequestDto);
        Contact savedContact = contactRepository.save(contact);

        return ContactMapper.mapToDto(savedContact);
    }

    @Override
    public List<ContactRequestDto> getAllContacts() {
        List<Contact> allContacts = contactRepository.findAll();
        return allContacts.stream()
                .map(ContactMapper::mapToDto)
                .toList();
    }
}
