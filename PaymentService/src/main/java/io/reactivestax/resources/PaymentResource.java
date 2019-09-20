package io.reactivestax.resources;

import io.reactivestax.domain.Invoices;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.domain.Vendors;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
public class PaymentResource {

    private Integer paymentId;
    private char fullPayment;

    private Integer createdBy;
    private Integer lastUpdatedBy;

    private Integer invoiceId;
    private Integer resourceTypeId;


    //private Invoices invoices;

   // private Vendors vendors;

    //private ResourceTypes resourceTypes;
}
