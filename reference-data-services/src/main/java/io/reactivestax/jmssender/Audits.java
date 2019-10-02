package io.reactivestax.jmssender;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class Audits {

    private Integer auditId;
    private String crudOperationType;
    private String entityName;
    private Integer entityId;
    private Date timestamp;
    private Integer userId;
}
