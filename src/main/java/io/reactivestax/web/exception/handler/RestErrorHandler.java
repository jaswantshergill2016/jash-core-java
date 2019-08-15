package io.reactivestax.web.exception.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import io.reactivestax.commons.utils.dto.DetailedMessage;
import io.reactivestax.commons.utils.dto.ErrorInfo;
import io.reactivestax.exception.AppRuntimeException;
import io.reactivestax.exception.NoDataFoundException;
import io.reactivestax.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.reactivestax.commons.constants.CommonConstants;
import io.reactivestax.commons.dto.RequestedResource;
import io.reactivestax.commons.utils.MessageUtils;
import io.reactivestax.exception.InvalidDataException;

/**
 * This class represents the global error handler for all REST based microservices
 * written using Spring REST framework.
 *
 */
@RestControllerAdvice
@Slf4j
public class RestErrorHandler {


    @Autowired private HttpServletRequest httpRequest;

    /**
     * This method handles all the cases where a given object fails the simple JSR-303
     * validation as annotated in the class definition using JSR-303 annotations like @NotBlank
     * (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
     */
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpStatus status) {
        log.error(CommonUtils.printStackTrace(ex));
        ErrorInfo errorInfo = new ErrorInfo();
        List<DetailedMessage> detailedMessages = new ArrayList<>();

        errorInfo.setDetailedMessages(detailedMessages);
        if (ex.getBindingResult() != null && ex.getBindingResult().getAllErrors() != null) {
            ex.getBindingResult().getAllErrors().forEach(objectError -> {
                DetailedMessage detailedMessage = new DetailedMessage();
                detailedMessage.setMessage(objectError.getDefaultMessage());
                detailedMessage.setObject(objectError.getObjectName());
                if(objectError instanceof FieldError){
                    detailedMessage.setField(((FieldError)objectError).getField());
                }
                detailedMessages.add(detailedMessage);
            });
        }

        errorInfo.setHttpStatusCode(status.toString());
        errorInfo.setHttpStatusMessage(status.getReasonPhrase());
        checkAndPopulateRequestURL(errorInfo);

        return new ResponseEntity(errorInfo, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentTypeMismatchException.class})

    public ResponseEntity<ErrorInfo> handlExceptions(
            Exception ex) {

        ErrorInfo errorInfo = populateErrorInfo(ex);
        checkAndPopulateRequestURL(errorInfo);
        errorInfo.setHttpStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorInfo.setHttpStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());

        return new ResponseEntity<>(
                errorInfo,
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //

    /**
     * This method handles all InValid data cases. They can be input data validation failures or busines rules
     * validation failures. And converts them into HTTP_404 Bad_request message highlighting the actual object.field
     * that caused the validation rule failure.
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorInfo> handleException(final InvalidDataException ex) {
        log.error(CommonUtils.printStackTrace(ex));
        ErrorInfo errorInfo = populateErrorInfo(ex);
        checkAndPopulateRequestURL(errorInfo);
        errorInfo.setHttpStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorInfo.setHttpStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is used by called APIs to signal that there's no data available for a given parameter value.
     * An HTTP_204 status code is sent to the calling client.
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorInfo> handleException(final NoDataFoundException ex) {
        log.error(CommonUtils.printStackTrace(ex));
        ErrorInfo errorInfo = populateErrorInfo(ex);
        checkAndPopulateRequestURL(errorInfo);
        errorInfo.setHttpStatusCode(HttpStatus.NO_CONTENT.toString());
        errorInfo.setHttpStatusMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
        return new ResponseEntity(errorInfo, HttpStatus.NO_CONTENT);
    }

    /**
     * This method handles the case when GET based requestParameter validation fails.
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(final ConstraintViolationException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        List<DetailedMessage> msg = new ArrayList<>();
        if (ex.getConstraintViolations() != null) {
            ex.getConstraintViolations().forEach(message -> {
                DetailedMessage detailedMessage = new DetailedMessage();
                detailedMessage.setMessage(message.getMessage());
                msg.add(detailedMessage);
            });
        }
        errorInfo.setDetailedMessages(msg);
        return new ResponseEntity(errorInfo, HttpStatus.BAD_REQUEST);
    }


    /**
     * This method handles all runtimeExceptions like Host-Not-Reachable or Network-down etc.
     * @param ex
     * @param request
     * @return ResponseEntity
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(final Throwable ex, WebRequest request) {
        log.error(CommonUtils.printStackTrace(ex));
        ErrorInfo errorInfo = new ErrorInfo();

        List<DetailedMessage> detailedMessages = new ArrayList<>();
        errorInfo.setDetailedMessages(detailedMessages);
        DetailedMessage detailedMessage = new DetailedMessage();
        checkAndPopulateRequestURL(errorInfo);
        errorInfo.getDetailedMessages().add(detailedMessage);
        errorInfo.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorInfo.setHttpStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ex.getMessage());
        strBuilder.append("---");
        strBuilder.append(CommonUtils.printStackTrace(ex));
        detailedMessage.setMessage(strBuilder.toString());

        return new ResponseEntity(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void checkAndPopulateRequestURL(ErrorInfo errorInfo) {
        RequestedResource rqResource = (RequestedResource)httpRequest.getAttribute(CommonConstants.REQUESTED_RESOURCE);
        if(rqResource!=null && StringUtils.isNotBlank(rqResource.getRequestUrl())){
            errorInfo.setUrl(rqResource.getRequestUri());
        }
    }




    private ErrorInfo populateErrorInfo(final Exception ex) {
        log.error(CommonUtils.printStackTrace(ex));
        ErrorInfo errorInfo = new ErrorInfo();
        List<DetailedMessage> detailedMessages = new ArrayList<>();

        if(ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException matme = (MethodArgumentTypeMismatchException)ex;
            String error = matme.getName() + " should be of type " + matme.getRequiredType().getName();

            DetailedMessage detailedMessage = new DetailedMessage();
            detailedMessage.setMessage(error);
            detailedMessage.setObject(matme.getName());

            detailedMessages.add(detailedMessage);

        } else if(ex instanceof HttpMessageNotReadableException || ex instanceof HttpRequestMethodNotSupportedException ) {

            String error = ex.getMessage();
            DetailedMessage detailedMessage = new DetailedMessage();
            detailedMessage.setMessage(error);

            detailedMessages.add(detailedMessage);

        } else if(ex instanceof AppRuntimeException) {
            AppRuntimeException are = (AppRuntimeException) ex;

            if (are.getErrorMap() != null) {

                are.getErrorMap().forEach((key,value) -> {
                    DetailedMessage dm = new DetailedMessage();
                    dm.setMessage(value);
                    dm.setObject(key);
                    detailedMessages.add(dm);
                });
            }
        }
        //

        errorInfo.setDetailedMessages(detailedMessages);

        return errorInfo;
    }
}