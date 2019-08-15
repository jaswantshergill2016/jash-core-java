package io.reactivestax.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "bootcamplocation")
@Table(name = "BOOTCAMP_LOCATION")
@NoArgsConstructor
@Getter
@Setter
public class BootcampLocation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "suite")
    private String suite;

    @Column(name = "street")
    private String street;

    @Column(name = "street_no")
    private String streetNo;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Bootcamp bootcamp;

}