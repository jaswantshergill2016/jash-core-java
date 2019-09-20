package io.reactivestax.domain;

//import com.reactivestax.domain.ExpenseTypes;
//import com.reactivestax.domain.ResourceTypes;
//import com.reactivestax.domain.Vendors;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@Entity
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger expenseId;
    private Double expenseAmt;
    private String expenseStatus;
    private String expenseDirection;
    private String expenseMode;
    private String expenseDesc;
    private char isShared;
    private Date createdDt = new Date();
    private Date lastUpdatedDt;
    private Integer createdBy;
    private Integer lastUpdatedBy;

    public Expenses(Double expenseAmt, String expenseStatus, String expenseDirection, String expenseMode, String expenseDesc, char isShared, Date lastUpdatedDt, Integer createdBy, Integer lastUpdatedBy) {
        this.expenseAmt = expenseAmt;
        this.expenseStatus = expenseStatus;
        this.expenseDirection = expenseDirection;
        this.expenseMode = expenseMode;
        this.expenseDesc = expenseDesc;
        this.isShared = isShared;
        this.lastUpdatedDt = lastUpdatedDt;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Vendors vendors;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ResourceTypes resourceTypes;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ExpenseTypes expenseTypes;
}