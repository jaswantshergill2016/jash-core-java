package io.reactivestax.controller;

//import io.reactivestax.api.model.RequestMetadata;
//import io.reactivestax.api.model.RestApiResponse;
//import io.reactivestax.api.model.RestApiStatus;

//import com.reactivestax.domain.Vendors;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.domain.Vendors;
import io.reactivestax.model.RequestMetadata;
import io.reactivestax.model.RestApiResponse;
import io.reactivestax.model.RestApiStatus;
import io.reactivestax.service.VendorService;
import io.reactivestax.utils.RequestUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;

//import io.reactivestax.exception.handler.UserNotFoundException;
//import io.reactivestax.service.UsersManagerService;
//import io.reactivestax.web.validator.UserRegistrationRequestValidator;

@RestController
@Slf4j
public class VendorController {

//    @Autowired
//    private UsersManagerService usersManagerService;

    @Autowired
    private VendorService vendorService;

    private SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //binder.setValidator(new UserRegistrationRequestValidator());
        //format.setLenient(false);
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));

    }

    @ApiOperation(value = "/api/v1/vendors/{vendorId}", nickname = "/api/v1/vendors/{vendorId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path = "/api/v1/vendors/{vendorId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,Vendors>> getVendor(@PathVariable String vendorId, HttpServletRequest request) {
        log.debug("Start getVendor {} ", vendorId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);
        RestApiResponse<RestApiStatus,Vendors> apiResponse = new RestApiResponse<>();

//        User user = usersManagerService.getUser(email);
//        if(user == null) {
//            throw new UserNotFoundException(email+" not found ");
//        }

        Vendors vendors = vendorService.getVendor(vendorId);
        //User user = new User();
        //Vendors vendors = new Vendors("vendor 1","vendorcode 1",1);
        apiResponse.setResponse(vendors);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_API_SUCCESS, HttpStatus.OK);
        apiResponse.setStatus(restApiStatus);

        ResponseEntity<RestApiResponse<RestApiStatus,Vendors>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.OK);

        log.debug("End getVendor ");
        return responseEntity;
    }


    @ApiOperation(value = "/api/v1/vendors", nickname = "/api/v1/vendors")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Failure")})
    @PostMapping(path = "/api/v1/vendors")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, Vendors>> registerVendor(@RequestBody @Valid Vendors vendors, HttpServletRequest request ) {
        log.debug("Start registerUser {} ", vendors);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, Vendors> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        vendorService.createVendor(vendors);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //vendors = new Vendors("vendor name 1", "vendor code 1",1);
        apiResponse.setResponse(vendors);

        ResponseEntity<RestApiResponse<RestApiStatus,Vendors>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End registerUser");
        return responseEntity;
    }

    @ApiOperation(value = "/api/v1/vendors/{vendorId}", nickname = "/api/v1/vendors/{vendorId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Failure")})
    @PutMapping(path = "/api/v1/vendors/{vendorId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,Vendors>> updateVendor(@RequestBody @Valid final Vendors vendor,@PathVariable String vendorId, HttpServletRequest request ) {
        log.debug("Start updateVendor {} ", vendor);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,Vendors> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        vendorService.updateVendor(vendor,vendorId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(vendor);

        ResponseEntity<RestApiResponse<RestApiStatus,Vendors>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End updateVendor");
        return responseEntity;
    }



    @ApiOperation(value = "/api/v1/vendors/{vendorId}", nickname = "/api/v1/vendors/{vendorId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Failure")})
    @DeleteMapping(path = "/api/v1/vendors/{vendorId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, Vendors>> deleteVendor(@RequestBody @Valid final Vendors vendor, @PathVariable String vendorId, HttpServletRequest request ) {
        log.debug("Start deleteVendor {} ", vendor);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,Vendors> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        vendorService.deleteVendor(vendorId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(vendor);

        ResponseEntity<RestApiResponse<RestApiStatus,Vendors>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End updateVendor");
        return responseEntity;
    }
}
