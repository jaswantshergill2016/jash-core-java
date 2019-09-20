package io.reactivestax.domain;

//import lombok.Getter;
//import lombok.Setter;
//import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import com.reactivestax.domain.Expenses;
//import com.reactivestax.domain.Invoices;
//import com.reactivestax.domain.Payments;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
//@Embeddable
public class Vendors {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private   Integer vendorId;
    private String vendorName;
    private String vendorCode;
    @JsonIgnore
    private Date createdDt = new Date();
    @JsonIgnore
    private Integer createdBy;

    public Vendors() {
    }

    public Vendors(String vendorName, String vendorCode, Integer createdBy) {
        this.vendorName = vendorName;
        this.vendorCode = vendorCode;
        this.createdBy = createdBy;
    }

    @OneToMany(
            mappedBy = "vendors",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Invoices> invoices = new ArrayList<>();

    @OneToMany(
            mappedBy = "vendors",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Expenses> expenses = new ArrayList<>();

    @OneToMany(
            mappedBy = "vendors",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Payments> payments = new ArrayList<>();

}
