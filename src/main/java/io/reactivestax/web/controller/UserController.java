package io.reactivestax.web.controller;


import io.reactivestax.api.model.ApiErrorsView;
import io.reactivestax.api.model.RequestMetadata;
import io.reactivestax.api.model.RestApiResponse;
import io.reactivestax.api.model.RestApiStatus;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.domain.User;
import io.reactivestax.exception.handler.UserNotFoundException;
import io.reactivestax.service.UsersManagerService;
import io.reactivestax.utils.RequestUtils;
import io.reactivestax.web.validator.UserRegistrationRequestValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UsersManagerService usersManagerService;

    private SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserRegistrationRequestValidator());
        format.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));

    }

    @ApiOperation(value = "/api/v1/users/{email}", nickname = "/api/v1/users/{email}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path = "/api/v1/users/{email}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,User>> getUser(@PathVariable String email, HttpServletRequest request) {
        log.debug("Start getUser {} ", email);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);
        RestApiResponse<RestApiStatus,User> apiResponse = new RestApiResponse<>();

        User user = usersManagerService.getUser(email);
        if(user == null) {
            throw new UserNotFoundException(email+" not found ");
        }
        apiResponse.setResponse(user);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_API_SUCCESS, HttpStatus.OK);
        apiResponse.setStatus(restApiStatus);

        ResponseEntity<RestApiResponse<RestApiStatus,User>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.OK);

        log.debug("End getUser ");
        return responseEntity;
    }


    @ApiOperation(value = "/api/v1/users", nickname = "/api/v1/users")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Failure")})
    @PostMapping(path = "/api/v1/users")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,User>> registerUser(@RequestBody @Valid final User user, HttpServletRequest request ) {
        log.debug("Start registerUser {} ", user);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,User> apiResponse = new RestApiResponse<>();
        usersManagerService.registerUser(user);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(user);

        ResponseEntity<RestApiResponse<RestApiStatus,User>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End registerUser");
        return responseEntity;
    }
}
