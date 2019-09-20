package io.reactivestax.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
