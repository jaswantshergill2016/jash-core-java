package io.reactivestax.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Sets up the accessLogsInterceptor to intercept specific paths
 *
 */
@Configuration
public class ApplicationConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Autowired
    AccessLogsHandlerInterceptor accessLogsHandlerInterceptor;



    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(accessLogsHandlerInterceptor).addPathPatterns("/api/**");


    }

}