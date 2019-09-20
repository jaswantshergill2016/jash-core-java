package io.reactivestax.domain;

//import com.reactivestax.domain.Invoices;
//import com.reactivestax.domain.ResourceTypes;
//import com.reactivestax.domain.Vendors;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentId;
    private char fullPayment;
    private Date createdDt =new Date();
    private Date lastUpdatedDt;
    private Integer createdBy;
    private Integer lastUpdatedBy;

    public Payments() {
    }

    public Payments(char fullPayment, Date lastUpdatedDt, Integer createdBy, Integer lastUpdatedBy) {
        this.fullPayment = fullPayment;
        this.lastUpdatedDt = lastUpdatedDt;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Invoices invoices;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Vendors vendors;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ResourceTypes resourceTypes;
}
