package com.reactivestax.domain;

//import lombok.Getter;
//import lombok.Setter;
//import net.bytebuddy.dynamic.loading.InjectionClassLoader;

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
public class Vendors {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private   Integer vendorId;
    private String vendorName;
    private String vendorCode;
    private Date createdDt = new Date();
    private Integer createdBy;

//    public Vendors() {
//    }

    public Vendors(String vendorName, String vendorCode, Integer createdBy) {
        this.vendorName = vendorName;
        this.vendorCode = vendorCode;
        this.createdBy = createdBy;
    }

    @OneToMany(
            mappedBy = "vendors",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Invoices> invoices = new ArrayList<>();

    @OneToMany(
            mappedBy = "vendors",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Expenses> expenses = new ArrayList<>();

    @OneToMany(
            mappedBy = "vendors",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Payments> payments = new ArrayList<>();



}
