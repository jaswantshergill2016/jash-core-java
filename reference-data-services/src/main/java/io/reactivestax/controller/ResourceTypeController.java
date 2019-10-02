package io.reactivestax.controller;

//import io.reactivestax.api.model.RequestMetadata;
//import io.reactivestax.api.model.RestApiResponse;
//import io.reactivestax.api.model.RestApiStatus;

//import com.reactivestax.domain.ResourceTypes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivestax.constants.ApiStatus;
//import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.jmssender.Audits;
import io.reactivestax.jmssender.Sender;
import io.reactivestax.model.RequestMetadata;
import io.reactivestax.model.RestApiResponse;
import io.reactivestax.model.RestApiStatus;
import io.reactivestax.service.ResourceTypeService;
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
import java.util.Date;

//import io.reactivestax.exception.handler.UserNotFoundException;
//import io.reactivestax.service.UsersManagerService;
//import io.reactivestax.web.validator.UserRegistrationRequestValidator;

@RestController
@Slf4j
public class ResourceTypeController {

//    @Autowired
//    private UsersManagerService usersManagerService;

    @Autowired
    private Sender sender;

    @Autowired
    private VendorService vendorService;
    @Autowired
    private ResourceTypeService resourceTypeService;

    private SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //binder.setValidator(new UserRegistrationRequestValidator());
        //format.setLenient(false);
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));

    }

    @ApiOperation(value = "/api/v1/resourceType/{resourceTypeId}", nickname = "/api/v1/resourceType/{resourceTypeId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path = "/api/v1/resourceType/{resourceTypeId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, ResourceTypes>> getVendor(@PathVariable String resourceTypeId, HttpServletRequest request) {
        log.debug("Start getresourceType {} ", resourceTypeId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);
        RestApiResponse<RestApiStatus,ResourceTypes> apiResponse = new RestApiResponse<>();

//        User user = usersManagerService.getUser(email);
//        if(user == null) {
//            throw new UserNotFoundException(email+" not found ");
//        }

        ResourceTypes resourceTypes = resourceTypeService.getResourceType(resourceTypeId);
        //User user = new User();
        //Vendors vendors = new Vendors("vendor 1","vendorcode 1",1);
        apiResponse.setResponse(resourceTypes);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_API_SUCCESS, HttpStatus.OK);
        apiResponse.setStatus(restApiStatus);

        ResponseEntity<RestApiResponse<RestApiStatus,ResourceTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.OK);

        log.debug("End getresourceTypes ");
        return responseEntity;
    }


    @ApiOperation(value = "/api/v1/resourceType", nickname = "/api/v1/resourceType")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PostMapping(path = "/api/v1/resourceType")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, ResourceTypes>> createResourceType(@RequestBody @Valid ResourceTypes resourceTypes, HttpServletRequest request ) {
        log.debug("Start createResourceType {} ", resourceTypes);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ResourceTypes> apiResponse = new RestApiResponse<>();
        resourceTypeService.createResourceType(resourceTypes);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(resourceTypes);

        ResponseEntity<RestApiResponse<RestApiStatus,ResourceTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End createResourceType");
        return responseEntity;
    }

    @ApiOperation(value = "/api/v1/resourceType/{resourceTypeId}", nickname = "/api/v1/resourceType/{resourceTypeId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PutMapping(path = "/api/v1/resourceType/{resourceTypeId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,ResourceTypes>> updateResourceType(@RequestBody @Valid final ResourceTypes resourceTypes,@PathVariable String resourceTypeId, HttpServletRequest request ) {
        log.debug("Start updateResourceType {} ", resourceTypes);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ResourceTypes> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        resourceTypeService.updateResourceType(resourceTypes,resourceTypeId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(resourceTypes);

        ResponseEntity<RestApiResponse<RestApiStatus,ResourceTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End updateResourceType");
        return responseEntity;
    }



    @ApiOperation(value = "/api/v1/resourceType/{resourceTypeId}", nickname = "/api/v1/resourceType/{resourceTypeId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @DeleteMapping(path = "/api/v1/resourceType/{resourceTypeId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,ResourceTypes>> deleteResourceType(@RequestBody @Valid final ResourceTypes resourceTypes,@PathVariable String resourceTypeId, HttpServletRequest request ) {
        log.debug("Start deleteResourceType {} ", resourceTypes);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, ResourceTypes> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        resourceTypeService.deleteResourceType(resourceTypeId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(resourceTypes);

        ResponseEntity<RestApiResponse<RestApiStatus,ResourceTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End deleteResourceType");
        return responseEntity;
    }
}
