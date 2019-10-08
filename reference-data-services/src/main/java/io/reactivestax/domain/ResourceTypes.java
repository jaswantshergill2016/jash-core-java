package io.reactivestax.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.reactivestax.domain.Expenses;
//import com.reactivestax.domain.Invoices;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
//@Embeddable
public class ResourceTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer resourceTypeId;
    private String resourceName;
    private String resourceNickName;
    @JsonIgnore
    private Date createdDt = new Date();

    private Integer createdBy;

    public ResourceTypes() {
    }

    public ResourceTypes(String resourceName, String resourceNickName, Integer createdBy) {
        this.resourceName = resourceName;
        this.resourceNickName = resourceNickName;
        this.createdBy = createdBy;
    }

    @OneToMany(
            mappedBy = "resourceTypes",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Invoices> invoices = new ArrayList<>();

    @OneToMany(
            mappedBy = "resourceTypes",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Expenses> expenses = new ArrayList<>();
}
