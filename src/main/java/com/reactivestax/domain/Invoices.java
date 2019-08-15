package com.reactivestax.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Entity
public class Invoices {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String invoiceId;

    //@EmbeddedId
    //private Integer vendorId;
    //@EmbeddedId
    private Integer resourceTypeId;
    private Double invoiceAmt;
    private String invoiceDesc;
    private Date createdDt = new Date();
    private Date lastUpdatedDt;
    private Integer createdBy;
    private Integer lastUpdatedBy;

    public Invoices(String invoiceId, Double invoiceAmt, String invoiceDesc, Date lastUpdatedDt, Integer createdBy, Integer lastUpdatedBy) {
        this.invoiceId = invoiceId;
        this.invoiceAmt = invoiceAmt;
        this.invoiceDesc = invoiceDesc;
        this.lastUpdatedDt = lastUpdatedDt;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Vendors vendors;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ResourceTypes resourceTypes;

    @OneToMany(
            mappedBy = "invoices",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Payments> payments = new ArrayList<>();

}