package io.reactivestax.service;


import lombok.extern.slf4j.Slf4j;
//import org.springframework.ws.WebServiceMessage;
//import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.tempuri.Add;
import org.tempuri.AddResponse;
import org.tempuri.Subtract;
import org.tempuri.SubtractResponse;

import java.io.ByteArrayOutputStream;


@Slf4j
public class CalculatorWebServiceClient extends WebServiceGatewaySupport {


	public AddResponse addNumbers(final Add add) {
		logger.debug("start numbers");



        ClientInterceptor interceptor = new ClientInterceptor()  {
            @Override
            public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
                log.debug("soap request is intercepted ");

                return true;
            }

            @Override
            public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
                log.debug("soap response is intercepted ");

                return true;
            }

            @Override
            public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
                log.debug("soap handleFault is intercepted ");

                return true;
            }

            @Override
            public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

            }
        };

        getWebServiceTemplate().setInterceptors(new ClientInterceptor[]{interceptor});


        final AddResponse response = (AddResponse) getWebServiceTemplate().marshalSendAndReceive(add);


		logger.debug("end numbers");
		return response;
	}

	public SubtractResponse subtractNumbers(final Subtract add) {
		logger.debug("start numbers");
		final SubtractResponse response = (SubtractResponse) getWebServiceTemplate().marshalSendAndReceive(add);
		logger.debug("end numbers");
		return response;
	}
	
}
