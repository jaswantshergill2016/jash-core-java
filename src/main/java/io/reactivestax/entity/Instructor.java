package io.reactivestax.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "instructor")
@Table(name = "INSTRUCTOR")
@NoArgsConstructor
@Setter
@Getter
public class Instructor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobileNo")
    private String mobileNo;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "BOOTCAMP_INSTRUCTOR_MAPPING",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "bootcamp_id")
    )
    private List<Bootcamp> bootcamps = new ArrayList<>() ;

    public void addBootcamp(Bootcamp newbootcamp) {
        bootcamps.add(newbootcamp);
    }
}