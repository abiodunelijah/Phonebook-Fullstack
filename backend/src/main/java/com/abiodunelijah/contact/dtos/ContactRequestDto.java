package com.abiodunelijah.contact.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactRequestDto {

    private Integer id;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s]?[0-9]{3}[-\\s]?[0-9]{4,5}$|^[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    private AddressRequestDto addressRequestDto;
}
