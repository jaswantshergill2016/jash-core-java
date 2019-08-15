package io.reactivestax.service;

import io.reactivestax.api.request.AddNumberRequest;
import io.reactivestax.api.response.AddNumberResponse;

public interface CalculatorService {

    String BEAN_ID = "calculatorService";

    AddNumberResponse addNumbers(AddNumberRequest addNumberRequest);
}
