package io.reactivestax.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STUDENT")
@NoArgsConstructor
@Setter
@Getter
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "email")
    private String email;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    @Column(name = "mobileNo")
    private String mobileNo;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Bootcamp bootcamp;
}