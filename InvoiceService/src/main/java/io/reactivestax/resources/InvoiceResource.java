package io.reactivestax.resources;

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
public class InvoiceResource {

    private Integer invoiceId;
    private Integer resourceTypeId;
    private Double invoiceAmt;
    private String invoiceDesc;
    private Integer createdBy;
    private Integer lastUpdatedBy;

    private Integer vendorId;
    //private Integer resourceTypesId;

    //private Vendors vendors;
    //private ResourceTypes resourceTypes;

}
