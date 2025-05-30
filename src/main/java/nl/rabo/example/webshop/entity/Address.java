package nl.rabo.example.webshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String houseNumber;
    private String postalCode;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private WebshopUser user;
}
