package io.reactivestax.api.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiFieldError {

    private String field;
    private String rejectedValue;
    private String errorCode;

    public ApiFieldError(String field, String rejectedValue, String errorCode) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.errorCode = errorCode;
    }
}
