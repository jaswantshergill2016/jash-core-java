package io.reactivestax.service;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.io.IOException;

@Configuration
public class CalculatorWebServiceConfiguration {

	static class ContentLengthHeaderRemover implements HttpRequestInterceptor {
		@Override
		public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
			request.removeHeaders(HTTP.CONTENT_LEN);


            request.removeHeaders("SOAPAction");
            request.addHeader("SOAPAction","http://tempuri.org/Add");
		}
	}
	
	@Autowired
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
	
	@Value("${application.service.soap-endpoint}")
	private String serviceEndPoint;

	@Bean
	public CalculatorWebServiceClient getCalculatorWebServiceClient(final Jaxb2Marshaller marshaller) {
		final CalculatorWebServiceClient client = new CalculatorWebServiceClient();
		client.setDefaultUri(serviceEndPoint);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		client.setMessageSender(messageSender());
		return client;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		final CloseableHttpClient httpClient = HttpClients.custom()
		        .setConnectionManager(poolingHttpClientConnectionManager).build();
		final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(requestFactory);
	}
	
	public String getServiceEndPoint() {
		return serviceEndPoint;
	}
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths(
		        "org.tempuri");
		return marshaller;
	}
	
	public HttpComponentsMessageSender messageSender() {
		final CloseableHttpClient httpClient = HttpClients.custom()
		        .addInterceptorFirst(new ContentLengthHeaderRemover())
		        //.setConnectionManager(poolingHttpClientConnectionManager).
                        .build();
		return new HttpComponentsMessageSender(httpClient);
	}

	public void setPoolingHttpClientConnectionManager(
	        final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
		this.poolingHttpClientConnectionManager = poolingHttpClientConnectionManager;
	}
	
	public void setServiceEndPoint(final String serviceEndPoint) {
		this.serviceEndPoint = serviceEndPoint;
	}

}
