package io.reactivestax.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TECHNOLOGY_REFERNCE")
@NoArgsConstructor
@Setter
@Getter
public class TechnologyReference {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Technology technology;
}