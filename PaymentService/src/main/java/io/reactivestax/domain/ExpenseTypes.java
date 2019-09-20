package io.reactivestax.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.reactivestax.domain.Expenses;

@Getter
@Setter
@Entity
public class ExpenseTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer expenseTypeId;

    private String expenseType;
    private String expenseDesc;
    @JsonIgnore
    private Date createdDt = new Date();
    @JsonIgnore
    private Integer createdBy;

    public ExpenseTypes() {
    }

    public ExpenseTypes(String expenseType, String expenseDesc, Integer createdBy) {
        this.expenseType = expenseType;
        this.expenseDesc = expenseDesc;
        this.createdBy = createdBy;
    }

    @OneToMany(
            mappedBy = "expenseTypes",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Expenses> expenses = new ArrayList<>();
}
