package io.reactivestax.exception.handler;


import io.reactivestax.api.model.*;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.utils.CommonUtils;
import io.reactivestax.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        logger.debug("start handleMethodArgumentNotValid");
        BindingResult bindingResult = ex
                .getBindingResult();


// Simple For Loop
        List<ApiFieldError> apiFieldErrors = new ArrayList<>();
        for(FieldError fieldError: bindingResult.getFieldErrors()) {
            apiFieldErrors.add(new ApiFieldError(
                    fieldError.getField(),
                    fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "",
                    fieldError.getCode()));
        }

// Lambda Expression
//        List<ApiFieldError> apiFieldErrors = bindingResult
//                .getFieldErrors()
//                .stream()
//                .map(fieldError -> new ApiFieldError(
//                        fieldError.getField(),
//                        fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "",
//                        fieldError.getCode())
//                )
//                .collect(Collectors.toList());

        RestApiResponse<RestApiStatus, ApiErrorsView> apiResponse = new RestApiResponse<>();
        RestApiStatus restApiStatus = prepareApiStatus(request, ApiStatus.NOT_READABLE_REQUEST_PAYLOAD, status,ex);

        ApiErrorsView apiErrorsView = new ApiErrorsView(apiFieldErrors);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(apiErrorsView);

        logger.debug("end handleMethodArgumentNotValid");

        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            WebRequest request,
            EntityNotFoundException ex) {

        RestApiResponse<RestApiStatus, Void> apiResponse = new RestApiResponse<>();
        RestApiStatus restApiStatus = prepareApiStatus(request, ApiStatus.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND,ex);

        apiResponse.setStatus(restApiStatus);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("start handleHttpMessageNotReadable");

        RestApiResponse<RestApiStatus, ApiErrorsView> apiResponse = new RestApiResponse<>();
        RestApiStatus restApiStatus = prepareApiStatus(request, ApiStatus.NOT_READABLE_REQUEST_PAYLOAD, status,ex);
        apiResponse.setStatus(restApiStatus);
        logger.debug("end handleHttpMessageNotReadable");

        return  new ResponseEntity<>(apiResponse, status);
    }


    private RestApiStatus prepareApiStatus(WebRequest request, ApiStatus apiStatus, HttpStatus status, Exception ex) {
        RequestMetadata requestMetadata = (RequestMetadata) request.getAttribute("request-metadata", 0);
        RestApiStatus restApiStatus =  RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), apiStatus, status);

        if(requestMetadata.isGenerateExceptionTrace())  {
            restApiStatus.setExceptionTrace(CommonUtils.getStackTrace(ex));
        }

        return restApiStatus;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            WebRequest request,
            DataIntegrityViolationException ex) {

        RestApiResponse<RestApiStatus, Void> apiResponse = new RestApiResponse<>();
        RestApiStatus restApiStatus = prepareApiStatus(request, ApiStatus.INVALID_PAYLOAD, HttpStatus.BAD_REQUEST,ex);

        apiResponse.setStatus(restApiStatus);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex,
                                         WebRequest request) {
        logger.debug("start handleException ");

        RestApiResponse<RestApiStatus, ApiErrorsView> apiResponse = new RestApiResponse<>();
        RestApiStatus restApiStatus = prepareApiStatus(request, ApiStatus.SYSTEM_UNABLE_TO_PROCESS_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR,ex);
        apiResponse.setStatus(restApiStatus);

        logger.debug("end handleException");

        return  new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
