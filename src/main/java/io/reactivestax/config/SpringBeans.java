package io.reactivestax.config;


import io.reactivestax.security.config.SSLContextConfigurator;
import io.reactivestax.service.CalculatorJaxWsWebserviceClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans {
	
	@Value("${application.max-connection-count}")
	private int maxConnectionCount;
	
	@Value("${application.max-per-route}")
	private int maxPerRoute;
	
	@Autowired
	private SSLContextConfigurator sslContextConfigurator;


	@Bean
	public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
		final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContextConfigurator.getSslContext(),
		        new NoopHostnameVerifier());
		
		final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
		        .<ConnectionSocketFactory>create().register("https", sslsf)
		        .register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
		
		final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
		        socketFactoryRegistry);
		connectionManager.setMaxTotal(maxConnectionCount);
		connectionManager.setDefaultMaxPerRoute(maxPerRoute);
		return connectionManager;
	}

	@Bean
	public CalculatorJaxWsWebserviceClient getCalculatorJaxwsWebserviceClient() {
		return new CalculatorJaxWsWebserviceClient();
	}


}
