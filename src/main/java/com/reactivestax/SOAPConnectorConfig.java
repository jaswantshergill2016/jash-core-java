package com.reactivestax;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
//i[; mport org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

//import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
//import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
//import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Configuration
public class SOAPConnectorConfig {
	/*
	@Value("${developer.program.username}")
	String userName;

	@Value("${developer.program.password}")
	String password;

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller
				.setContextPath("ca.canadapost.ws.soap.postoffice");
		return marshaller;
	}

	@Bean
	public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
		SOAPConnector client = new SOAPConnector();
		client.setDefaultUri("http://localhost:8899/service/track-springws/");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		ClientInterceptor[] interceptors = new ClientInterceptor[] {securityInterceptor()};
		client.setInterceptors(interceptors);

		return client;
	}

	@Bean
	public Wss4jSecurityInterceptor securityInterceptor(){
		Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
		wss4jSecurityInterceptor.setSecurementActions("Timestamp UsernameToken");
		wss4jSecurityInterceptor.setSecurementUsername(userName);
		wss4jSecurityInterceptor.setSecurementPassword(password);
		wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
		return wss4jSecurityInterceptor;
	}
*/
}
