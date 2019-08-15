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
public class ExpenseTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer expenseTypeId;

    private String expenseType;
    private String expenseDesc;
    private Date createdDt = new Date();
    private Integer createdBy;

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
    private List<Expenses> expenses = new ArrayList<>();
}
