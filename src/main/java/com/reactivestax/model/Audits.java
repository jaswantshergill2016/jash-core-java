package com.reactivestax.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Audits {

    private Integer auditId;
    private String crudOperationType;
    private String entityName;
    private Integer entityId;
    private Date timestamp;
    private Integer userId;
}
