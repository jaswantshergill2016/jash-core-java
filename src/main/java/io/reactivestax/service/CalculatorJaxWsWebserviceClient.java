package io.reactivestax.service;

import lombok.extern.slf4j.Slf4j;
import org.tempuri.*;

import javax.xml.ws.BindingProvider;


@Slf4j
public class CalculatorJaxWsWebserviceClient {


    private CalculatorSoap calculatorSoap;


    public CalculatorJaxWsWebserviceClient() {
        Calculator calculator = new Calculator();

        calculatorSoap  = calculator.getCalculatorSoap();

        //changing the ending
        BindingProvider bp = (BindingProvider) calculatorSoap;
        //change endpoint here
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://www.dneonline.com/calculator.asmx"); //
    }


    public AddResponse addNumbers(final Add add) {
        log.debug("start numbers");

        final int result =  calculatorSoap.add(add.getIntA(),add.getIntB());

        AddResponse response = new AddResponse();
        response.setAddResult(result);
        log.debug("end numbers");
        return response;
    }

    public AddResponse subtractNumbers(final Add add) {
        log.debug("start numbers");

        final int result =  calculatorSoap.subtract(add.getIntA(),add.getIntB());

        AddResponse response = new AddResponse();
        response.setAddResult(result);
        log.debug("end numbers");
        return response;
    }



}
