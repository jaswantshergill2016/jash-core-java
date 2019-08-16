package io.reactivestax.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RestApiStatus implements Serializable {

    private String requestId;

    private LocalDateTime responseTime;

    private String statusDesc;

    private int statusCode;

    private int httpStatusCode;

    private String httpStatusDesc;

    private String exceptionTrace;
}
