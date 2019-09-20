package io.reactivestax.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RestApiResponse<RestApiStatus, R> {

    private RestApiStatus status;
    private R response;

    public RestApiResponse() {
    }

    public RestApiResponse(RestApiStatus status, R response) {
        this.status = status;
        this.response = response;
    }
}
