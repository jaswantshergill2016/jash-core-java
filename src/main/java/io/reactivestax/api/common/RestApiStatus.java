package io.reactivestax.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RestApiStatus implements Serializable {

    private String requestId;

    private Long responseTime;

    private String statusDesc;

    private int statusCode;

    private int httpStatusCode;

    private String httpStatusDesc;

    private String exceptionTrace;
}
