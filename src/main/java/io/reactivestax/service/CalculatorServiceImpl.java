package io.reactivestax.service;

import io.reactivestax.api.request.AddNumberRequest;
import io.reactivestax.api.response.AddNumberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.Add;
import org.tempuri.AddResponse;

import java.util.Random;


@Service(CalculatorService.BEAN_ID)
@Slf4j
public class CalculatorServiceImpl implements CalculatorService {


    @Autowired
    private CalculatorWebServiceClient calculatorWebServiceClient;

    @Autowired
    private CalculatorJaxWsWebserviceClient calculatorJaxwsWebserviceClient;

    @Override
    public AddNumberResponse addNumbers(AddNumberRequest addNumberRequest) {
        log.debug("start findCurrencyConversionRate {} ", addNumberRequest);


        Add add = new Add();
        add.setIntA(addNumberRequest.getNumber1());
        add.setIntB(addNumberRequest.getNumber2());

        //AddResponse response  = calculatorWebServiceClient.addNumbers(add);

        AddResponse response  = calculatorJaxwsWebserviceClient.addNumbers(add);

        sleepRandomly();


        log.debug("end findCurrencyConversionRate");

        return new AddNumberResponse(response.getAddResult());
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
