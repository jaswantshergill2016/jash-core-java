package com.reactivestax;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
@ToString
@Data
public class Audits {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer auditId;
    private String crudOperationType;
    private String entityName;
    private Integer entityId;
    private Date timestamp;
    private Integer userId;
}
