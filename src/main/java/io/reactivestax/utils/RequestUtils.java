package io.reactivestax.utils;

import io.reactivestax.api.common.RestApiStatus;
import io.reactivestax.constants.ApiStatus;
import org.springframework.http.HttpStatus;

public class RequestUtils {

    public static RestApiStatus buildRestApiStatus(String requestId, ApiStatus apiStatus, HttpStatus status) {

        RestApiStatus restApiStatus = new RestApiStatus();

        restApiStatus.setRequestId(requestId);
        restApiStatus.setResponseTime(System.currentTimeMillis());

        restApiStatus.setStatusCode(apiStatus.getStatusCode());
        restApiStatus.setStatusDesc(apiStatus.getStatusDesc());

        restApiStatus.setHttpStatusCode(status.value());
        restApiStatus.setHttpStatusDesc(status.getReasonPhrase());

        return restApiStatus;
    }
 }
