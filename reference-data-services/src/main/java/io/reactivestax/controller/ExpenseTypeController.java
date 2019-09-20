package io.reactivestax.controller;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.domain.ExpenseTypes;
import io.reactivestax.model.RequestMetadata;
import io.reactivestax.model.RestApiResponse;
import io.reactivestax.model.RestApiStatus;
import io.reactivestax.service.ExpenseTypeService;
import io.reactivestax.service.ResourceTypeService;
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

@RestController
@Slf4j
public class ExpenseTypeController {


    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private ExpenseTypeService expenseTypeService;


    private SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //binder.setValidator(new UserRegistrationRequestValidator());
        //format.setLenient(false);
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));

    }

    @ApiOperation(value = "/api/v1/expenseType/{expenseTypeId}", nickname = "/api/v1/expenseType/{expenseTypeId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path = "/api/v1/expenseType/{expenseTypeId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, ExpenseTypes>> getExpenseType(@PathVariable String expenseTypeId, HttpServletRequest request) {
        log.debug("Start getexpenseType {} ", expenseTypeId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);
        RestApiResponse<RestApiStatus,ExpenseTypes> apiResponse = new RestApiResponse<>();

//        User user = usersManagerService.getUser(email);
//        if(user == null) {
//            throw new UserNotFoundException(email+" not found ");
//        }

        ExpenseTypes expenseTypes = expenseTypeService.getExpenseType(expenseTypeId);
        //User user = new User();
        //Vendors vendors = new Vendors("vendor 1","vendorcode 1",1);
        apiResponse.setResponse(expenseTypes);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_API_SUCCESS, HttpStatus.OK);
        apiResponse.setStatus(restApiStatus);

        ResponseEntity<RestApiResponse<RestApiStatus,ExpenseTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.OK);

        log.debug("End getExpenseTypes ");
        return responseEntity;
    }


    @ApiOperation(value = "/api/v1/expenseType", nickname = "/api/v1/expenseType")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PostMapping(path = "/api/v1/expenseType")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, ExpenseTypes>> createExpenseType(@RequestBody @Valid ExpenseTypes expenseTypes, HttpServletRequest request ) {
        log.debug("Start createResourceType {} ", expenseTypes);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ExpenseTypes> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        expenseTypeService.createExpenseType(expenseTypes);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //vendors = new Vendors("vendor name 1", "vendor code 1",1);
        apiResponse.setResponse(expenseTypes);

        ResponseEntity<RestApiResponse<RestApiStatus,ExpenseTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End createExpenseTypes");
        return responseEntity;
    }

    @ApiOperation(value = "/api/v1/expenseType/{expenseTypeId}", nickname = "/api/v1/expenseType/{expenseTypeId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PutMapping(path = "/api/v1/expenseType/{expenseTypeId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,ExpenseTypes>> updateExpenseType(@RequestBody @Valid final ExpenseTypes expenseTypes,@PathVariable String expenseTypeId, HttpServletRequest request ) {
        log.debug("Start updateResourceType {} ", expenseTypes);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ExpenseTypes> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        expenseTypeService.updateExpenseType(expenseTypes,expenseTypeId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(expenseTypes);

        ResponseEntity<RestApiResponse<RestApiStatus,ExpenseTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End updateExpenseType");
        return responseEntity;
    }



    @ApiOperation(value = "/api/v1/expenseType/{expenseTypeId}", nickname = "/api/v1/expenseType/{expenseTypeId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @DeleteMapping(path = "/api/v1/expenseType/{expenseTypeId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,ExpenseTypes>> deleteExpenseType(@PathVariable String expenseTypeId, HttpServletRequest request ) {
        log.debug("Start deleteExpenseType {} ", expenseTypeId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ExpenseTypes> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        expenseTypeService.deleteExpenseType(expenseTypeId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //apiResponse.setResponse(resourceTypes);

        ResponseEntity<RestApiResponse<RestApiStatus,ExpenseTypes>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End deleteResourceType");
        return responseEntity;
    }
}
