package com.abiodunelijah.contact.services.impl;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.dtos.ContactSearchDto;
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
                .orElseThrow(()-> new NotFoundException("contact by with" + contactId+ " does not exist"));
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
    public List<ContactSearchDto> searchContacts(ContactSearchDto searchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> contactRoot = criteriaQuery.from(Contact.class);

        // Join with Address entity for address-related searches
        Join<Contact, Address> addressJoin = contactRoot.join("address", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        // Search by firstName (case-insensitive, partial match)
        if (searchDto.getFirstName() != null && !searchDto.getFirstName().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(contactRoot.get("firstName")),
                    "%" + searchDto.getFirstName().toLowerCase() + "%"
            ));
        }

        // Search by lastName (case-insensitive, partial match)
        if (searchDto.getLastName() != null && !searchDto.getLastName().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(contactRoot.get("lastName")),
                    "%" + searchDto.getLastName().toLowerCase() + "%"
            ));
        }

        // Search by phoneNumber (exact match)
        if (searchDto.getPhoneNumber() != null) {
            predicates.add(criteriaBuilder.equal(
                    contactRoot.get("phoneNumber"),
                    searchDto.getPhoneNumber()
            ));
        }

        // Search by streetNumber (case-insensitive, partial match)
        if (searchDto.getStreetNumber() != null && !searchDto.getStreetNumber().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(addressJoin.get("streetNumber")),
                    "%" + searchDto.getStreetNumber().toLowerCase() + "%"
            ));
        }

        // Search by streetName (case-insensitive, partial match)
        if (searchDto.getStreetName() != null && !searchDto.getStreetName().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(addressJoin.get("streetName")),
                    "%" + searchDto.getStreetName().toLowerCase() + "%"
            ));
        }

        // Search by postalCode (case-insensitive, partial match)
        if (searchDto.getPostalCode() != null && !searchDto.getPostalCode().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(addressJoin.get("postalCode")),
                    "%" + searchDto.getPostalCode().toLowerCase() + "%"
            ));
        }

        // Search by state (case-insensitive, partial match)
        if (searchDto.getState() != null && !searchDto.getState().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(addressJoin.get("state")),
                    "%" + searchDto.getState().toLowerCase() + "%"
            ));
        }

        // Search by country (case-insensitive, partial match)
        if (searchDto.getCountry() != null && !searchDto.getCountry().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(addressJoin.get("country")),
                    "%" + searchDto.getCountry().toLowerCase() + "%"
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

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
