package com.reactivestax;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

import javax.servlet.Servlet;
import java.util.List;
import java.util.Properties;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  @Bean
  public ServletRegistrationBean<Servlet> messageDispatcherServlet(
      ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet =
        new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);

    return new ServletRegistrationBean<>(servlet,
        "/service/track-springws/*");
  }

  @Bean(name = "tracking")
  public Wsdl11Definition defaultWsdl11Definition() {
    SimpleWsdl11Definition wsdl11Definition =
        new SimpleWsdl11Definition();
    wsdl11Definition
        .setWsdl(new ClassPathResource("/wsdl/track.wsdl"));

    return wsdl11Definition;
  }
/*
  @Bean
  public SimplePasswordValidationCallbackHandler securityCallbackHandler(){
    SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
    Properties users = new Properties();
    users.setProperty("admin", "secret");
    callbackHandler.setUsers(users);
    return callbackHandler;
  }

  @Bean
  public Wss4jSecurityInterceptor securityInterceptor(){
    Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
    securityInterceptor.setValidationActions("Timestamp UsernameToken");
      securityInterceptor.setSecurementPasswordType("PasswordText");
    securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
    return securityInterceptor;
  }

  @Override
  public void addInterceptors(List interceptors) {
    interceptors.add(securityInterceptor());
  }
*/
}
