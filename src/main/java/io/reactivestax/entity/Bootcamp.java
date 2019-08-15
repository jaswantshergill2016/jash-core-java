package io.reactivestax.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity(name = "bootcamp")
@Table(name = "BOOTCAMP")
@NoArgsConstructor
@Getter
@Setter
public class Bootcamp {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "BOOTCAMP_INSTRUCTOR_MAPPING",
            joinColumns = @JoinColumn(name = "bootcamp_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    private List<Instructor> instructors = new ArrayList<>();

    @OneToMany(
            mappedBy = "bootcamp",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Student> students = new ArrayList<>();


    @OneToMany(
            mappedBy = "bootcamp",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Technology> technologies = new ArrayList<>();

    //here bootcamp_location table would add bootcamp_id
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bootcamp")
    private BootcampLocation bootcampLocation;

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }
}