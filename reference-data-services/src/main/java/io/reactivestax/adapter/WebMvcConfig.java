package io.reactivestax.adapter;

import io.reactivestax.interceptor.RequestMetadataInterceptor;
import io.reactivestax.interceptor.RequestTimingInterceptor;
//import io.reactivestax.web.interceptor.RequestMetadataInterceptor;
//import io.reactivestax.web.interceptor.RequestTimingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestTimingInterceptor()).addPathPatterns("/api/v1/**");
        registry.addInterceptor(new RequestMetadataInterceptor()).addPathPatterns("/api/v1/**");
    }
}
