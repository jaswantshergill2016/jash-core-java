package com.reactivestax;

import ca.canadapost.ws.soap.postoffice.GetTrackingDetailRequest;
import ca.canadapost.ws.soap.postoffice.GetTrackingDetailResponse;
import ca.canadapost.ws.soap.postoffice.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ws.client.core.WebServiceTemplate;

@SpringBootApplication
public class CanadaPostSpringWsClientApplication implements CommandLineRunner {

	///@Autowired
	//private SOAPConnector soapConnector;

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	public static void main(String[] args) {

		SpringApplication.run(CanadaPostSpringWsClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		getTrackingDetailsRequest();
	}
	public void getTrackingDetailsRequest() {
		ObjectFactory objectFactory = new ObjectFactory();
		GetTrackingDetailRequest getTrackingDetailRequest = objectFactory.createGetTrackingDetailRequest();
		getTrackingDetailRequest.setPin("1371134583769923");
		//GetTrackingSummaryResponse getTrackingSummaryResponse = (GetTrackingSummaryResponse)soapConnector.callWebService("https://ct.soa-gw.canadapost.ca/rs/soap/postoffice", getTrackingSummaryRequest);
		GetTrackingDetailResponse getTrackingDetailResponse = null;
		//getTrackingDetailResponse=(GetTrackingDetailResponse) soapConnector.callWebService("https://localhost:8899/service/track-springws/", getTrackingDetailRequest);
		getTrackingDetailResponse = (GetTrackingDetailResponse) webServiceTemplate.marshalSendAndReceive(getTrackingDetailRequest);

		GetTrackingDetailResponse.TrackingDetail trackingDetail = getTrackingDetailResponse.getTrackingDetail();
		System.out.println("==================================");
		System.out.println(trackingDetail.getChangedExpectedDate());
		System.out.println(trackingDetail.getDestinationPostalId());
		System.out.println(trackingDetail.getCustomerRef1());
		System.out.println(trackingDetail.getMailedByCustomerNumber());
		System.out.println(trackingDetail.getOriginalPin());
		System.out.println(trackingDetail.getReturnPin());
	}



}
