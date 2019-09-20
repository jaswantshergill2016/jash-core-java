package io.reactivestax.interceptor;


import io.micrometer.core.instrument.util.StringUtils;
import io.reactivestax.model.RequestMetadata;
import io.reactivestax.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

//import io.reactivestax.api.model.RequestMetadata;
//import org.apache.commons.lang3.StringUtils;

/**
 * Request interceptor to capture the request metadata
 */
@Slf4j
public class RequestMetadataInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_METADATA = "request-metadata";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("start preHandle");
        String requestMetadataPayload = request.getHeader(REQUEST_METADATA);
        RequestMetadata requestMetadata;

        if(StringUtils.isNotEmpty(requestMetadataPayload)) {
            try {
                requestMetadata = RequestMetadata.class.cast(CommonUtils.unmarshallJson(requestMetadataPayload,RequestMetadata.class));
                if(requestMetadata.getRequestId()==null)
                    requestMetadata = generateDefaultRequestMetadata();
            } catch (Exception ex) {
                log.error("Exception parsing requestMetadata {} ",requestMetadataPayload, ex);
                log.debug("Generating default request metadata");
                requestMetadata = generateDefaultRequestMetadata();
            }
        } else {
            log.debug("request metadata is  missing, initializing default ");
            requestMetadata = generateDefaultRequestMetadata();
        }
        request.setAttribute(REQUEST_METADATA,requestMetadata);
        log.debug("end preHandle "+REQUEST_METADATA+" {} ",requestMetadata);

        return super.preHandle(request, response, handler);
    }


    private RequestMetadata generateDefaultRequestMetadata() {
        RequestMetadata requestMetadata = new RequestMetadata();
        requestMetadata.setLanguage("EN");
        requestMetadata.setRequestId(UUID.randomUUID().toString());
        requestMetadata.setRequestTime(System.currentTimeMillis());
        return requestMetadata;
    }
}
