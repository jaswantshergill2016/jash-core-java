package com.reactivestax;

import ca.canadapost.ws.soap.track.GetTrackingDetailRequest;
import ca.canadapost.ws.soap.track.GetTrackingDetailResponse;
import ca.canadapost.ws.soap.track.ObjectFactory;
//import io.reactivestax.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class TrackingEndpoint {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(TrackingEndpoint.class);

  @PayloadRoot(
      namespace = "http://www.canadapost.ca/ws/soap/track",
         // namespace = "http://www.canadapost.ca/ws/soap/track",

     // localPart = "getTrackingDetailRequest")
         localPart = "get-tracking-detail-request")
          //localPart = "GetTrackingDetail")

  @ResponsePayload
  public GetTrackingDetailResponse GetTrackingDetail(@RequestPayload GetTrackingDetailRequest getTrackingDetailRequest) {
    LOGGER.info("Endpoint received getTrackingDetailRequest",getTrackingDetailRequest.getPin());

    ObjectFactory objectFactory = new ObjectFactory();
    GetTrackingDetailResponse getTrackingDetailResponse = objectFactory.createGetTrackingDetailResponse();
    GetTrackingDetailResponse.TrackingDetail trackingDetail = new GetTrackingDetailResponse.TrackingDetail();
    trackingDetail.setCustomerRef1("CustomerRef1");
    getTrackingDetailResponse.setTrackingDetail(trackingDetail);
    LOGGER.info("Endpoint sending getTrackingDetailResponse",
            getTrackingDetailResponse.getTrackingDetail().toString());
    return getTrackingDetailResponse;
  }
}