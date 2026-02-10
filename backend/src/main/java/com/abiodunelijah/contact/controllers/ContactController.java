package com.abiodunelijah.contact.controllers;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/add-contact")
    public ResponseEntity<ContactRequestDto> addContact(@RequestBody ContactRequestDto contactRequestDto){
        ContactRequestDto contactRequest = contactService.addContact(contactRequestDto);
        return new ResponseEntity<>(contactRequest, HttpStatus.CREATED);
    }

    @GetMapping("/all-contacts")
    public ResponseEntity<List<ContactRequestDto>> getAllContacts(){
        List<ContactRequestDto> allContacts = contactService.getAllContacts();
        return new ResponseEntity<>(allContacts, HttpStatus.OK);
    }

    @GetMapping("/get-contact/{id}")
    public ResponseEntity<ContactRequestDto> getContact(@PathVariable Integer id){
        ContactRequestDto contactRequestDto = contactService.getContact(id);
        return new ResponseEntity<>(contactRequestDto, HttpStatus.OK);
    }

    @PutMapping("/update-contact/{id}")
    public ResponseEntity<ContactRequestDto> updateContact(@PathVariable("id") Integer contactId, @RequestBody ContactRequestDto contactRequestDto){
        ContactRequestDto contactRequest = contactService.updateContact(contactId, contactRequestDto);
        return new ResponseEntity<>(contactRequest, HttpStatus.OK);
    }
}
