package io.reactivestax.controller;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.domain.Expenses;
import io.reactivestax.domain.Payments;
import io.reactivestax.handler.InvoiceNotFoundException;
import io.reactivestax.model.RequestMetadata;
import io.reactivestax.model.RestApiResponse;
import io.reactivestax.model.RestApiStatus;
import io.reactivestax.resources.ExpensesResource;
import io.reactivestax.resources.PaymentResource;
import io.reactivestax.service.ExpensesService;
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
public class PaymentController {

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    private PaymentService paymentService;


    private SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //binder.setValidator(new UserRegistrationRequestValidator());
        //format.setLenient(false);
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));

    }

    @ApiOperation(value = "/api/v1/payment/{paymentId}", nickname = "/api/v1/payment/{paymentId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping(path = "/api/v1/payment/{paymentId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> getExpenseType(@PathVariable String paymentId, HttpServletRequest request) {
        log.debug("Start getpayment {} ", paymentId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);
        RestApiResponse<RestApiStatus, PaymentResource> apiResponse = new RestApiResponse<>();

//        User user = usersManagerService.getUser(email);
//        if(user == null) {
//            throw new UserNotFoundException(email+" not found ");
//        }

       // Expenses expensess = expenseTypeService.getExpenseType(expensesId);

        Payments payments = paymentService.getPayment(paymentId);

        PaymentResource paymentResource = new PaymentResource();

        paymentResource.setPaymentId(payments.getPaymentId());
        paymentResource.setFullPayment(payments.getFullPayment());
        paymentResource.setCreatedBy(payments.getCreatedBy());
        paymentResource.setLastUpdatedBy(payments.getLastUpdatedBy());



        //ExpensesResource expensess = null;
        //User user = new User();
        //Vendors vendors = new Vendors("vendor 1","vendorcode 1",1);
        apiResponse.setResponse(paymentResource);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_API_SUCCESS, HttpStatus.OK);
        apiResponse.setStatus(restApiStatus);

        ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.OK);

        log.debug("End getpayment ");
        return responseEntity;
    }


    @ApiOperation(value = "/api/v1/payment", nickname = "/api/v1/payment")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PostMapping(path = "/api/v1/payment")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> createpayment(@RequestBody @Valid PaymentResource paymentResource, HttpServletRequest request ) {
        log.debug("Start createResourceType {} ", paymentResource);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, PaymentResource> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //expensesService.createExpenseType(expensess);

        Payments payment = new Payments();


        payment.setPaymentId(paymentResource.getPaymentId());
        payment.setFullPayment(paymentResource.getFullPayment());
        payment.setCreatedBy(paymentResource.getCreatedBy());
        payment.setLastUpdatedBy(paymentResource.getLastUpdatedBy());

        try {
            String INVOICE_I_API_PATH =
                    "http://invoiceservice/api/v1/invoice/" + paymentResource.getInvoiceId() + "/";
                    //"http://localhost:8383/api/v1/invoice/" + paymentResource.getInvoiceId() + "/";
            log.info(INVOICE_I_API_PATH + "+++++++++++++++++++++++");
            //ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(INVOICE_I_API_PATH, String.class);
            ResponseEntity<RestApiResponse> responseEntity1 = restTemplate.getForEntity(INVOICE_I_API_PATH, RestApiResponse.class);
            log.info(String.valueOf(responseEntity1));
            if(!responseEntity1.getStatusCode().is2xxSuccessful()){
                throw new InvoiceNotFoundException("Resource not found "+INVOICE_I_API_PATH);
            }
        }catch(RestClientException ex){
            throw new InvoiceNotFoundException(ex.getMessage());
        }
        paymentService.createPayment(payment);

        paymentResource.setPaymentId(payment.getPaymentId());

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //vendors = new Vendors("vendor name 1", "vendor code 1",1);
        apiResponse.setResponse(paymentResource);

        ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End createpayment");
        return responseEntity;
    }

    @ApiOperation(value = "/api/v1/payment/{paymentId}", nickname = "/api/v1/payment/{paymentId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @PutMapping(path = "/api/v1/payment/{paymentId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> updateExpenseType(@RequestBody @Valid final PaymentResource paymentResource, @PathVariable String paymentId, HttpServletRequest request ) {
        log.debug("Start updateResourceType {} ", paymentResource);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, PaymentResource> apiResponse = new RestApiResponse<>();

        Payments payment = new Payments();

        payment.setPaymentId(paymentResource.getPaymentId());
        payment.setFullPayment(paymentResource.getFullPayment());
        payment.setCreatedBy(paymentResource.getCreatedBy());
        payment.setLastUpdatedBy(paymentResource.getLastUpdatedBy());

        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        //expenseTypeService.updateExpenseType(expensess,expensesId);
        paymentService.updatePayment(payment,paymentId);

        paymentResource.setPaymentId(Integer.parseInt(paymentId));

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        apiResponse.setResponse(paymentResource);

        ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End updatepayment");
        return responseEntity;
    }



    @ApiOperation(value = "/api/v1/payment/{paymentId}", nickname = "/api/v1/payment/{paymentId}")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @DeleteMapping(path = "/api/v1/payment/{paymentId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> deleteExpenseType(@PathVariable String paymentId, HttpServletRequest request ) {
        log.debug("Start deletepayment {} ", paymentId);
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute("request-metadata");

        log.debug("Request metadata {} ",requestMetadata);

        RestApiResponse<RestApiStatus, PaymentResource> apiResponse = new RestApiResponse<>();
        //usersManagerService.registerUser(user);
        //usersManagerService.updateUser(user,userId);
        //vendorService.updateVendor(vendor,vendorId);
        //expenseTypeService.deleteExpenseType(expensesId);
        paymentService.deletePayment(paymentId);
        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestId(), ApiStatus.USER_CREATED, HttpStatus.CREATED);
        apiResponse.setStatus(restApiStatus);
        //apiResponse.setResponse(resourceTypes);

        ResponseEntity<RestApiResponse<RestApiStatus, PaymentResource>> responseEntity = new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

        log.debug("End deletepayment");
        return responseEntity;
    }
}
