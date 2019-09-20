package io.reactivestax.resources;

//import com.reactivestax.domain.ExpenseTypes;
//import com.reactivestax.domain.ResourceTypes;
//import com.reactivestax.domain.Vendors;
import io.reactivestax.domain.ExpenseTypes;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.domain.Vendors;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
public class ExpensesResource {

    private Integer expenseId;
    private Double expenseAmt;
    private String expenseStatus;
    private String expenseDirection;
    private String expenseMode;
    private String expenseDesc;
    private char isShared;
    private Integer createdBy;
    private Integer lastUpdatedBy;

    private Integer vendorId;
    private Integer resourceTypesId;
    private Integer expenseTypesId;

    //private Vendors vendors;
    //private ResourceTypes resourceTypes;
    //private ExpenseTypes expenseTypes;
}