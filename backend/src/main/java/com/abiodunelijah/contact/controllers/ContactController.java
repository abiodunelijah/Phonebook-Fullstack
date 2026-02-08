package com.abiodunelijah.contact.controllers;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private ContactService contactService;

    @PostMapping("/add-contact")
    public ResponseEntity<ContactRequestDto> addContact(@RequestBody ContactRequestDto contactRequestDto){
        ContactRequestDto contactRequest = contactService.addContact(contactRequestDto);
        return new ResponseEntity<>(contactRequest, HttpStatus.CREATED);
    }
}
