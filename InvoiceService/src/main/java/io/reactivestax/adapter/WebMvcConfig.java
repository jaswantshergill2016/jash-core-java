package io.reactivestax.adapter;

import io.reactivestax.interceptor.RequestMetadataInterceptor;
import io.reactivestax.interceptor.RequestTimingInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import io.reactivestax.web.interceptor.RequestMetadataInterceptor;
//import io.reactivestax.web.interceptor.RequestTimingInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestTimingInterceptor()).addPathPatterns("/api/v1/**");
        registry.addInterceptor(new RequestMetadataInterceptor()).addPathPatterns("/api/v1/**");
    }

    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
