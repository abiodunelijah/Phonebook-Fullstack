package com.abiodunelijah.contact.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

}
