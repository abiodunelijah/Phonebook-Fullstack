package com.abiodunelijah.contact.services.impl;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.entities.Address;
import com.abiodunelijah.contact.entities.Contact;
import com.abiodunelijah.contact.mappers.ContactMapper;
import com.abiodunelijah.contact.repositories.ContactRepository;
import com.abiodunelijah.contact.services.ContactService;
import com.abiodunelijah.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public ContactRequestDto getContact(Integer contactId) {
        Contact contactByPhoneNumber = contactRepository.findById(contactId)
                .orElseThrow(()-> new NotFoundException("contact by with " + contactId + " does not exist"));
        return ContactMapper.mapToDto(contactByPhoneNumber);
    }

    @Override
    public ContactRequestDto updateContact(Integer contactId, ContactRequestDto contactRequestDto) {

        Contact existingContactById = contactRepository.findById(contactId)
                .orElseThrow(() -> new NotFoundException("contact with id " + contactId + " does not exist"));

        // Update existing contact fields
        existingContactById.setFirstName(contactRequestDto.getFirstName());
        existingContactById.setLastName(contactRequestDto.getLastName());
        existingContactById.setPhoneNumber(contactRequestDto.getPhoneNumber());

        // Update address if provided
        if (contactRequestDto.getAddressRequestDto() != null) {
            if (existingContactById.getAddress() == null) {
                existingContactById.setAddress(new Address());
            }
            existingContactById.getAddress().setStreetNumber(contactRequestDto.getAddressRequestDto().getStreetNumber());
            existingContactById.getAddress().setStreetName(contactRequestDto.getAddressRequestDto().getStreetName());
            existingContactById.getAddress().setPostalCode(contactRequestDto.getAddressRequestDto().getPostalCode());
            existingContactById.getAddress().setState(contactRequestDto.getAddressRequestDto().getState());
            existingContactById.getAddress().setCountry(contactRequestDto.getAddressRequestDto().getCountry());
        }

        Contact savedContact = contactRepository.save(existingContactById);

        return ContactMapper.mapToDto(savedContact);
    }
    
    @Override
    public void deleteContact(Integer contactId) {
        Contact existingContact = contactRepository.findById(contactId)
                .orElseThrow(() -> new NotFoundException("contact with id " + contactId + " does not exist"));

        contactRepository.delete(existingContact);
    }

    @Override
    public List<ContactRequestDto> searchContacts(String firstName,String lastName, String phoneNumber) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> contactRoot = criteriaQuery.from(Contact.class);
        List<Predicate> predicates = new ArrayList<>();

        // Search by firstName (case-insensitive, partial match)
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(contactRoot.get("firstName")),
                    "%" + firstName.toLowerCase() + "%"
            ));
        }

        // Search by lastName (case-insensitive, partial match)
        if (lastName!= null && !lastName.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(contactRoot.get("lastName")),
                    "%" + lastName.toLowerCase() + "%"
            ));
        }

        // Search by phoneNumber (exact match)
        if (phoneNumber != null) {
            predicates.add(criteriaBuilder.equal(
                    contactRoot.get("phoneNumber"),
                    phoneNumber
            ));
        }

        // Combine all predicates with AND
        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        // Order by lastName, then firstName
        criteriaQuery.orderBy(
                criteriaBuilder.asc(contactRoot.get("lastName")),
                criteriaBuilder.asc(contactRoot.get("firstName"))
        );

        // Execute query and map Contact entities to ContactSearchDto
        List<Contact> contacts = entityManager.createQuery(criteriaQuery).getResultList();
        
        return contacts.stream()
                .map(contact -> ContactRequestDto.builder()
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .phoneNumber(contact.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());
    }

}
