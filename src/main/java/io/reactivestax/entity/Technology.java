package io.reactivestax.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TECHNOLOGY")
@NoArgsConstructor
@Setter
@Getter
public class Technology {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Bootcamp bootcamp;
}