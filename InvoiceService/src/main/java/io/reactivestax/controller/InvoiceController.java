package io.reactivestax.controller;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.domain.Invoices;
import io.reactivestax.domain.Payments;
import io.reactivestax.handler.ResourceTypeNotFoundException;
import io.reactivestax.handler.VendorNotFoundException;
import io.reactivestax.model.RequestMetadata;
import io.reactivestax.model.RestApiResponse;
import io.reactivestax.model.RestApiStatus;
import io.reactivestax.resources.InvoiceResource;
import io.reactivestax.resources.PaymentResource;
import io.reactivestax.service.InvoiceService;
import io.reactivestax.service.PaymentService;
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
public class InvoiceController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InvoiceService invoiceService;


    private SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //binder.setValidator(new UserRegistrationRequestValidator());
        //format.setLenient(false);
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));

    }

    @ApiOperation(value = "/api/v1/invoice/{invoiceId}", nickname = "/api/v1/invoice/{invoiceId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path = "/api/v1/invoice/{invoiceId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> getExpenseType(@PathVariable String invoiceId, HttpServletRequest request) {
        log.debug("Start getinvoice {} ", invoiceId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);
        RestApiResponse<RestApiStatus, InvoiceResource> apiResponse = new RestApiResponse<>();

//        User user = usersManagerService.getUser(email);
//        if(user == null) {
//            throw new UserNotFoundException(email+" not found ");
//        }

       // Expenses expensess = expenseTypeService.getExpenseType(expensesId);

        Invoices invoices = invoiceService.getInvoice(invoiceId);

        InvoiceResource invoiceResource = new InvoiceResource();


        invoiceResource.setInvoiceId(invoices.getInvoiceId());
        invoiceResource.setResourceTypeId(invoices.getResourceTypeId());
        invoiceResource.setInvoiceAmt(invoices.getInvoiceAmt());
        invoiceResource.setInvoiceDesc(invoices.getInvoiceDesc());
        invoiceResource.setCreatedBy(invoices.getCreatedBy());
        invoiceResource.setLastUpdatedBy(invoices.getLastUpdatedBy());



        //ExpensesResource expensess = null;
        //User user = new User();
        //Vendors vendors = new Vendors("vendor 1","vendorcode 1",1);
        apiResponse.setResponse(invoiceResource);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_API_SUCCESS, HttpStatus.OK);
        apiResponse.setStatus(restApiStatus);

        ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.OK);

        log.debug("End getinvoice ");
        return responseEntity;
    }


    @ApiOperation(value = "/api/v1/invoice", nickname = "/api/v1/invoice")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PostMapping(path = "/api/v1/invoice")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> createInvoice(@RequestBody @Valid InvoiceResource invoiceResource, HttpServletRequest request ) {
        log.debug("Start createResourceType {} ", invoiceResource);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, InvoiceResource> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //expensesService.createExpenseType(expensess);

        Invoices invoices = new Invoices();

        //invoiceResource.setInvoiceId(invoices.getInvoiceId());
        invoices.setResourceTypeId(invoiceResource.getResourceTypeId());
        invoices.setInvoiceAmt(invoiceResource.getInvoiceAmt());
        invoices.setInvoiceDesc(invoiceResource.getInvoiceDesc());
        invoices.setCreatedBy(invoiceResource.getCreatedBy());
        invoices.setLastUpdatedBy(invoiceResource.getLastUpdatedBy());


        try {
            String RESOURCETYPE_I_API_PATH =
                    "http://referencedataservice/api/v1/resourceType/" + invoiceResource.getResourceTypeId() + "/";
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
            String VENDOR_I_API_PATH =
                    "http://referencedataservice/api/v1/vendors/" + invoiceResource.getVendorId() + "/";
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


        invoiceService.createInvoice(invoices);

        invoiceResource.setInvoiceId(invoices.getInvoiceId());

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //vendors = new Vendors("vendor name 1", "vendor code 1",1);
        apiResponse.setResponse(invoiceResource);

        ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End createinvoice");
        return responseEntity;
    }

    @ApiOperation(value = "/api/v1/invoice/{invoiceId}", nickname = "/api/v1/invoice/{invoiceId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PutMapping(path = "/api/v1/invoice/{invoiceId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> updateInvoice(@RequestBody @Valid final InvoiceResource invoiceResource, @PathVariable String invoiceId, HttpServletRequest request ) {
        log.debug("Start updateResourceType {} ", invoiceResource);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, InvoiceResource> apiResponse = new RestApiResponse<>();

        Invoices invoices = new Invoices();

        invoices.setResourceTypeId(invoiceResource.getResourceTypeId());
        invoices.setInvoiceAmt(invoiceResource.getInvoiceAmt());
        invoices.setInvoiceDesc(invoiceResource.getInvoiceDesc());
        invoices.setCreatedBy(invoiceResource.getCreatedBy());
        invoices.setLastUpdatedBy(invoiceResource.getLastUpdatedBy());

        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        //expenseTypeService.updateExpenseType(expensess,expensesId);
        invoiceService.updateInvoice(invoices,invoiceId);

        invoiceResource.setInvoiceId(Integer.parseInt(invoiceId));

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(invoiceResource);

        ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End updateinvoice");
        return responseEntity;
    }



    @ApiOperation(value = "/api/v1/invoice/{invoiceId}", nickname = "/api/v1/invoice/{invoiceId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @DeleteMapping(path = "/api/v1/invoice/{invoiceId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> deleteInvoice(@PathVariable String invoiceId, HttpServletRequest request ) {
        log.debug("Start deleteInvoice {} ", invoiceId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, InvoiceResource> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        //expenseTypeService.deleteExpenseType(expensesId);
        invoiceService.deleteInvoice(invoiceId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //apiResponse.setResponse(resourceTypes);

        ResponseEntity<RestApiResponse<RestApiStatus, InvoiceResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End deleteinvoice");
        return responseEntity;
    }
}
