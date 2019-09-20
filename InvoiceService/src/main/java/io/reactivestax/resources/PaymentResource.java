package io.reactivestax.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResource {

    private Integer paymentId;
    private char fullPayment;

    private Integer createdBy;
    private Integer lastUpdatedBy;


    //private Invoices invoices;

   // private Vendors vendors;

    //private ResourceTypes resourceTypes;
}
