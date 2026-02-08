package com.abiodunelijah.contact.entities;

import com.abiodunelijah.contact.dtos.AddressRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Address extends AddressRequestDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

}
