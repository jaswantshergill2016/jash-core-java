package io.reactivestax.web.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestTimingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTimingInterceptor.class);

    private static final String REQUEST_TIME = "startRequestTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQUEST_TIME,System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        Long requestEndTime = System.currentTimeMillis();
        Long requestStartTime =  (Long)request.getAttribute(REQUEST_TIME);

        logger.debug("Request processing time for URI {} , Method {} , is {} ",request.getRequestURI(),request.getMethod(),(requestEndTime-requestStartTime));

        super.postHandle(request, response, handler, modelAndView);
    }
}
