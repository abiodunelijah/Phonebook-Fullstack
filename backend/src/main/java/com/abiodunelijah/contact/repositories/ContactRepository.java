package com.abiodunelijah.contact.repositories;

import com.abiodunelijah.contact.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    boolean existsContactByPhoneNumber(String phoneNumber);

}
