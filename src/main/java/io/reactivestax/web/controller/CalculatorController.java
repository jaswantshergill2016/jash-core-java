package io.reactivestax.web.controller;


import io.reactivestax.api.common.RestApiResponse;
import io.reactivestax.api.common.RestApiStatus;
import io.reactivestax.api.request.AddNumberRequest;
import io.reactivestax.api.response.AddNumberResponse;
import io.reactivestax.commons.constants.CommonConstants;
import io.reactivestax.commons.dto.RequestMetadata;
import io.reactivestax.constants.ApiStatus;
import io.reactivestax.service.CalculatorService;
import io.reactivestax.utils.RequestUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Random;

@RestController
@Slf4j
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;


    @ApiOperation(value = "/api/add", nickname = "/api/add")
    @ApiResponses(value = { //
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/api/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestApiResponse addNumbers(@RequestBody @Valid final AddNumberRequest addNumberRequest,
                                                                       HttpServletRequest request) {

        this.validateRequest(addNumberRequest);


        RestApiResponse<RestApiStatus,AddNumberResponse> apiResponse = new RestApiResponse<>();
        RequestMetadata requestMetadata = (RequestMetadata)request.getAttribute(CommonConstants.REQUEST_METADATA_HEADER);

        RestApiStatus restApiStatus = RequestUtils.buildRestApiStatus(requestMetadata.getRequestUniqueId(),
                ApiStatus.CURRENCYCONVERSION_REQUEST_PROCESSED,
                HttpStatus.OK);

        AddNumberResponse addNumberResponse = calculatorService.addNumbers(addNumberRequest);

        apiResponse.setResponse(addNumberResponse);
        apiResponse.setStatus(restApiStatus);

        return apiResponse;
    }


    private void validateRequest(AddNumberRequest addNumberRequest) {

        log.debug("*************8  calling validaterequesr for {} ",addNumberRequest);

        sleepRandomly();
    }


    private void sleepRandomly() {
        int minSleepTime = 100;
        try
        {
            Random r= new Random();
            int low = 20;
            int high = 100;
            int  delay = r.nextInt(high-low);
            log.debug("Adding executing delay (ms) for the methid {} ",delay);
            Thread.sleep(minSleepTime+delay);
        } catch (InterruptedException ie) {
            log.error("Exception during sleep ",ie);
        }
    }




}
