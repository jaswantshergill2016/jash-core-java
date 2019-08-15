package com.reactivestax.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
//@Embeddable
public class ResourceTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer resourceTypeId;
    private String resourceName;
    private String resourceNickName;
    private Date createdDt = new Date();
    private Integer createdBy;

    public ResourceTypes(String resourceName, String resourceNickName, Integer createdBy) {
        this.resourceName = resourceName;
        this.resourceNickName = resourceNickName;
        this.createdBy = createdBy;
    }

    @OneToMany(
            mappedBy = "resourceTypes",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Invoices> invoices = new ArrayList<>();

    @OneToMany(
            mappedBy = "resourceTypes",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Expenses> expenses = new ArrayList<>();
}
