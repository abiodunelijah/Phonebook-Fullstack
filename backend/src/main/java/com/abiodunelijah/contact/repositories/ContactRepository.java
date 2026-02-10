package com.abiodunelijah.contact.repositories;

import com.abiodunelijah.contact.dtos.ContactSearchDto;
import com.abiodunelijah.contact.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    boolean existsContactByPhoneNumber(Integer phoneNumber);
    Contact findContactByPhoneNumber(Integer phoneNumber);
    List<Contact> searchContacts(ContactSearchDto searchDto);
}
