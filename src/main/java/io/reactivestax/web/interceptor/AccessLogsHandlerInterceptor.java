package io.reactivestax.web.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import io.reactivestax.commons.dto.RequestMetadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import io.reactivestax.commons.constants.CommonConstants;
import io.reactivestax.commons.dto.RequestedResource;
import io.reactivestax.exception.InvalidDataException;

/**
 * The accessLogsInterceptor for all microservices. It retrieves the request-metadata
 * from the requestHeaders and persists it in logs. In near future the support for persisting the logs
 * into a persistent store will be turned on as well. 
 */
@Component
@Slf4j
public class AccessLogsHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final String REQUEST_URL_STR_CONSTANT= "Request URL::";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("entered preHandle");
        
        RequestedResource rqResource = new RequestedResource();
        rqResource.setRequestUri(request.getRequestURI());        
        String requestUrl = StringUtils.isNotBlank(request.getRequestURL()) ? request.getRequestURL().toString() : "";
        rqResource.setRequestUrl(requestUrl);
        request.setAttribute(CommonConstants.REQUESTED_RESOURCE, rqResource);
//
        if (StringUtils.isNotBlank(request.getHeader(CommonConstants.REQUEST_METADATA_HEADER))) {
            String requestMetaDataJsonStr = request.getHeader(CommonConstants.REQUEST_METADATA_HEADER);
            processRequestMetaData(requestMetaDataJsonStr, request);
        } else {
            InvalidDataException invalidDataException = new InvalidDataException();
            invalidDataException.getErrorMap().put(CommonConstants.REQUEST_METADATA_HEADER,
                "request_metadata request header cannot be missing or have blank value");
            throw invalidDataException;
        }
        // log the basic perf metrics
        logEntryTimings(request);
        //
        log.debug("exited preHandle");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("entered afterCompletion");
        // basic perf metrics logs
        logExitTimings(request);
        //
        log.debug("exited afterCompletion");
    }

    private void processRequestMetaData(String requestMetaDataHeader, HttpServletRequest request) {
        deserializeRequestMetadata(requestMetaDataHeader, request);
        RequestMetadata requestMetadata = (RequestMetadata) request.getAttribute(CommonConstants.REQUEST_METADATA);
        log.debug("logging requestMetaData: " + requestMetadata);
    }

    private void deserializeRequestMetadata(String requestMetaDataJsonStr, HttpServletRequest request) {
        com.google.gson.Gson gson = new Gson();


        //
        RequestMetadata requestMetadata;
        try {
            requestMetadata = gson.fromJson(requestMetaDataJsonStr, RequestMetadata.class);
            request.setAttribute("request-metadata",requestMetadata);
        } catch (Exception e) {
        	log.error(e.getMessage());
            InvalidDataException invalidDataException = new InvalidDataException(e);
            invalidDataException.getErrorMap().put(CommonConstants.REQUEST_METADATA_HEADER,
                "Error while parsing the request_metadata headerr=" + requestMetaDataJsonStr);
            throw invalidDataException;
        }
        validateRequestMetadata(requestMetadata);
        request.setAttribute(CommonConstants.REQUEST_METADATA, requestMetadata);
    }

    private void validateRequestMetadata(RequestMetadata requestMetadata) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RequestMetadata>> constraintViolations = validator.validate(requestMetadata);
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            InvalidDataException invalidDataException = new InvalidDataException();
            for (ConstraintViolation<RequestMetadata> constraintViolation : constraintViolations) {
                String propertyPath = constraintViolation.getPropertyPath().toString();
                String message = constraintViolation.getMessage();
                invalidDataException.getErrorMap().put(propertyPath, message);
            }
            throw invalidDataException;
        }
    }

    private void logExitTimings(HttpServletRequest request) {
        long startTime = (Long) request.getAttribute("startTime");
        log.info(REQUEST_URL_STR_CONSTANT + request.getRequestURL().toString() + ":: End Time=" + System.currentTimeMillis());
        log.info(REQUEST_URL_STR_CONSTANT + request.getRequestURL().toString() + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
    }

    private void logEntryTimings(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        log.info(REQUEST_URL_STR_CONSTANT + request.getRequestURL().toString() + ":: Start Time=" + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
    }
}