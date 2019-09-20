package io.reactivestax.controller;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.domain.ExpenseTypes;
import io.reactivestax.domain.Expenses;
import io.reactivestax.handler.ExpenseTypeNotFoundException;
import io.reactivestax.handler.ResourceTypeNotFoundException;
import io.reactivestax.handler.VendorNotFoundException;
import io.reactivestax.model.RequestMetadata;
import io.reactivestax.model.RestApiResponse;
import io.reactivestax.model.RestApiStatus;
import io.reactivestax.resources.ExpensesResource;
import io.reactivestax.service.ExpenseTypeService;
import io.reactivestax.service.ExpensesService;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;

@RestController
@Slf4j
public class ExpensesController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExpensesService expensesService;


    private SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //binder.setValidator(new UserRegistrationRequestValidator());
        //format.setLenient(false);
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));

    }

    @ApiOperation(value = "/api/v1/expenses/{expensesId}", nickname = "/api/v1/expenses/{expensesId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path = "/api/v1/expenses/{expensesId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, ExpensesResource>> getExpenseType(@PathVariable String expensesId, HttpServletRequest request) {
        log.debug("Start getexpenses {} ", expensesId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);
        RestApiResponse<RestApiStatus,ExpensesResource> apiResponse = new RestApiResponse<>();

//        User user = usersManagerService.getUser(email);
//        if(user == null) {
//            throw new UserNotFoundException(email+" not found ");
//        }

       // Expenses expensess = expenseTypeService.getExpenseType(expensesId);

        Expenses expenses = expensesService.getExpense(expensesId);

        ExpensesResource expensesResource = new ExpensesResource();

        expensesResource.setExpenseId(expenses.getExpenseId());
        expensesResource.setExpenseAmt(expenses.getExpenseAmt());
        expensesResource.setExpenseDesc(expenses.getExpenseDesc());
        expensesResource.setCreatedBy(expenses.getCreatedBy());
        expensesResource.setExpenseDirection(expenses.getExpenseDirection());
        expensesResource.setExpenseStatus(expenses.getExpenseStatus());
        expensesResource.setExpenseMode(expenses.getExpenseMode());
        expensesResource.setIsShared(expenses.getIsShared());
        expensesResource.setLastUpdatedBy(expenses.getLastUpdatedBy());


        //ExpensesResource expensess = null;
        //User user = new User();
        //Vendors vendors = new Vendors("vendor 1","vendorcode 1",1);
        apiResponse.setResponse(expensesResource);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_API_SUCCESS, HttpStatus.OK);
        apiResponse.setStatus(restApiStatus);

        ResponseEntity<RestApiResponse<RestApiStatus,ExpensesResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.OK);

        log.debug("End getExpenses ");
        return responseEntity;
    }


    @ApiOperation(value = "/api/v1/expenses", nickname = "/api/v1/expenses")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PostMapping(path = "/api/v1/expenses")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, ExpensesResource>> createExpenses(@RequestBody @Valid ExpensesResource expensesResource, HttpServletRequest request ) {
        log.debug("Start createResourceType {} ", expensesResource);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ExpensesResource> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //expensesService.createExpenseType(expensess);

        Expenses expenses = new Expenses();
        expenses.setExpenseAmt(expensesResource.getExpenseAmt());
        expenses.setExpenseDesc(expensesResource.getExpenseDesc());
        expenses.setCreatedBy(expensesResource.getCreatedBy());
        expenses.setExpenseDirection(expensesResource.getExpenseDirection());
        expenses.setExpenseStatus(expensesResource.getExpenseStatus());
        expenses.setExpenseMode(expensesResource.getExpenseMode());
        expenses.setIsShared(expensesResource.getIsShared());
        expenses.setLastUpdatedBy(expensesResource.getLastUpdatedBy());


        try {
            String RESOURCETYPE_I_API_PATH =
                    "http://referencedataservice/api/v1/resourceType/" + expensesResource.getResourceTypesId() + "/";
            //"http://localhost:8383/api/v1/resourceType/" + invoiceResource.getResourceTypeId() + "/";
            log.info(RESOURCETYPE_I_API_PATH + "+++++++++++++++++++++++");
            //ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(RESOURCETYPE_I_API_PATH, String.class);
            ResponseEntity<RestApiResponse> responseEntity1 = restTemplate.getForEntity(RESOURCETYPE_I_API_PATH, RestApiResponse.class);
            log.info(String.valueOf(responseEntity1));
            if(!responseEntity1.getStatusCode().is2xxSuccessful()){
                throw new ResourceTypeNotFoundException("Resource not found "+RESOURCETYPE_I_API_PATH);
            }
        }catch(RestClientException ex){
            throw new ResourceTypeNotFoundException(ex.getMessage());
        }

        try {
            String EXPENSETYPE_I_API_PATH =
                    "http://referencedataservice/api/v1/expenseType/" + expensesResource.getExpenseTypesId() + "/";
            //"http://localhost:8383/api/v1/expenseType/" + expensesResource.getExpenseTypesId() + "/";
            log.info(EXPENSETYPE_I_API_PATH + "+++++++++++++++++++++++");
            //ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(EXPENSETYPE_I_API_PATH, String.class);
            ResponseEntity<RestApiResponse> responseEntity1 = restTemplate.getForEntity(EXPENSETYPE_I_API_PATH, RestApiResponse.class);
            log.info(String.valueOf(responseEntity1));
            if(!responseEntity1.getStatusCode().is2xxSuccessful()){
                throw new ExpenseTypeNotFoundException("Resource not found "+EXPENSETYPE_I_API_PATH);
            }
        }catch(RestClientException ex){
            throw new ExpenseTypeNotFoundException(ex.getMessage());
        }

        try {
            String VENDOR_I_API_PATH =
                    "http://referencedataservice/api/v1/vendors/" + expensesResource.getVendorId() + "/";
            //"http://localhost:8383/api/v1/vendors/" + invoiceResource.getVendorId() + "/";
            log.info(VENDOR_I_API_PATH + "+++++++++++++++++++++++");
            //ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(VENDOR_I_API_PATH, String.class);
            ResponseEntity<RestApiResponse> responseEntity1 = restTemplate.getForEntity(VENDOR_I_API_PATH, RestApiResponse.class);
            log.info(String.valueOf(responseEntity1));
            if(!responseEntity1.getStatusCode().is2xxSuccessful()){
                throw new VendorNotFoundException("Resource not found "+VENDOR_I_API_PATH);
            }
        }catch(RestClientException ex){
            throw new VendorNotFoundException(ex.getMessage());
        }

        expensesService.createExpenses(expenses);

        expensesResource.setExpenseId(expenses.getExpenseId());

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //vendors = new Vendors("vendor name 1", "vendor code 1",1);
        apiResponse.setResponse(expensesResource);

        ResponseEntity<RestApiResponse<RestApiStatus,ExpensesResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End createExpenses");
        return responseEntity;
    }

    @ApiOperation(value = "/api/v1/expenses/{expensesId}", nickname = "/api/v1/expenses/{expensesId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PutMapping(path = "/api/v1/expenses/{expensesId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,ExpensesResource>> updateExpenseType(@RequestBody @Valid final ExpensesResource expensesResource, @PathVariable String expensesId, HttpServletRequest request ) {
        log.debug("Start updateResourceType {} ", expensesResource);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ExpensesResource> apiResponse = new RestApiResponse<>();

        Expenses expenses = new Expenses();
        expenses.setExpenseId(expensesResource.getExpenseId());
        expenses.setExpenseAmt(expensesResource.getExpenseAmt());
        expenses.setExpenseDesc(expensesResource.getExpenseDesc());
        expenses.setCreatedBy(expensesResource.getCreatedBy());
        expenses.setExpenseDirection(expensesResource.getExpenseDirection());
        expenses.setExpenseStatus(expensesResource.getExpenseStatus());
        expenses.setExpenseMode(expensesResource.getExpenseMode());
        expenses.setIsShared(expensesResource.getIsShared());
        expenses.setLastUpdatedBy(expensesResource.getLastUpdatedBy());

        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        //expenseTypeService.updateExpenseType(expensess,expensesId);
        expensesService.updateExpense(expenses,expensesId);

        expensesResource.setExpenseId(Integer.parseInt(expensesId));

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(expensesResource);

        ResponseEntity<RestApiResponse<RestApiStatus,ExpensesResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End updateExpenses");
        return responseEntity;
    }



    @ApiOperation(value = "/api/v1/expenses/{expensesId}", nickname = "/api/v1/expenses/{expensesId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @DeleteMapping(path = "/api/v1/expenses/{expensesId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus,ExpensesResource>> deleteExpenseType(@PathVariable String expensesId, HttpServletRequest request ) {
        log.debug("Start deleteExpenses {} ", expensesId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus,ExpensesResource> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        //expenseTypeService.deleteExpenseType(expensesId);
        expensesService.deleteExpense(expensesId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //apiResponse.setResponse(resourceTypes);

        ResponseEntity<RestApiResponse<RestApiStatus, ExpensesResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End deleteExpenses");
        return responseEntity;
    }
}
