package com.abiodunelijah.contact.controllers;

import com.abiodunelijah.contact.dtos.ContactRequestDto;
import com.abiodunelijah.contact.services.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    /**
     * Create a new contact
     * POST /api/contacts
     */
    @PostMapping
    public ResponseEntity<ContactRequestDto> createContact(@Valid @RequestBody ContactRequestDto contactRequestDto) {
        ContactRequestDto createdContact = contactService.addContact(contactRequestDto);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    /**
     * Get all contacts
     * GET /api/contacts
     */
    @GetMapping
    public ResponseEntity<List<ContactRequestDto>> getAllContacts() {
        List<ContactRequestDto> allContacts = contactService.getAllContacts();
        return new ResponseEntity<>(allContacts, HttpStatus.OK);
    }

    /**
     * Get single contact by ID
     * GET /api/contacts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactRequestDto> getContactById(@PathVariable Integer id) {
        ContactRequestDto contactRequestDto = contactService.getContact(id);
        return new ResponseEntity<>(contactRequestDto, HttpStatus.OK);
    }

    /**
     * Update a contact
     * PUT /api/contacts/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContactRequestDto> updateContact(
            @PathVariable("id") Integer contactId,
            @Valid @RequestBody ContactRequestDto contactRequestDto) {
        ContactRequestDto updatedContact = contactService.updateContact(contactId, contactRequestDto);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    /**
     * Delete a contact
     * DELETE /api/contacts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id") Integer contactId) {
        contactService.deleteContact(contactId);
        return new ResponseEntity<>("Contact deleted successfully", HttpStatus.OK);
    }

    /**
     * Search contacts by multiple criteria
     * GET /api/contacts/search?firstName=john&lastName=Doe&phoneNumber=555
     */
    @GetMapping("/search")
    public ResponseEntity<List<ContactRequestDto>> searchContacts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNumber) {
        List<ContactRequestDto> contacts = contactService.searchContacts(firstName, lastName, phoneNumber);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    // Keep old endpoints for backward compatibility
    /**
     * Legacy endpoint - redirects to POST /api/contacts
     * @deprecated Use POST /api/contacts instead
     */
    @PostMapping("/add-contact")
    public ResponseEntity<ContactRequestDto> addContact(@Valid @RequestBody ContactRequestDto contactRequestDto) {
        return createContact(contactRequestDto);
    }

    /**
     * Legacy endpoint - redirects to GET /api/contacts
     * @deprecated Use GET /api/contacts instead
     */
    @GetMapping("/all-contacts")
    public ResponseEntity<List<ContactRequestDto>> getAllContactsLegacy() {
        return getAllContacts();
    }

    /**
     * Legacy endpoint - redirects to GET /api/contacts/{id}
     * @deprecated Use GET /api/contacts/{id} instead
     */
    @GetMapping("/get-contact/{id}")
    public ResponseEntity<ContactRequestDto> getContactLegacy(@PathVariable Integer id) {
        return getContactById(id);
    }

    /**
     * Legacy endpoint - redirects to PUT /api/contacts/{id}
     * @deprecated Use PUT /api/contacts/{id} instead
     */
    @PutMapping("/update-contact/{id}")
    public ResponseEntity<ContactRequestDto> updateContactLegacy(
            @PathVariable("id") Integer contactId,
            @RequestBody ContactRequestDto contactRequestDto) {
        return updateContact(contactId, contactRequestDto);
    }

    /**
     * Legacy endpoint - redirects to DELETE /api/contacts/{id}
     * @deprecated Use DELETE /api/contacts/{id} instead
     */
    @DeleteMapping("/delete-contact/{id}")
    public ResponseEntity<String> deleteContactLegacy(@PathVariable("id") Integer contactId) {
        return deleteContact(contactId);
    }
}


