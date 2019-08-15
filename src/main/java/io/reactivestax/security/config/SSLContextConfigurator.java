package io.reactivestax.security.config;

import io.reactivestax.commons.utils.MessageUtils;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

@Component
public class SSLContextConfigurator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SSLContextConfigurator.class);
	
	private SSLContext sslContext;
	
	public SSLContextConfigurator(//
                                  @Value("classpath:${keystore.file}") final Resource keystoreFile, //
                                  @Value("${keystore.type}") final String keystoreType, //
                                  @Value("${keystore.pass}") final String keystorePass,

                                  @Value("classpath:${truststore.file}") final Resource truststoreFile,
                                  @Value("${truststore.type}") final String truststoreType,
                                  @Value("${truststore.pass}") final String truststorePass) {

		try {
			final KeyStore keyStore = KeyStore.getInstance(keystoreType);
			keyStore.load(keystoreFile.getInputStream(), keystorePass.toCharArray());

			final KeyStore trustStore = KeyStore.getInstance(truststoreType);
			trustStore.load(truststoreFile.getInputStream(), truststorePass.toCharArray());

			final TrustManagerFactory trustManager = TrustManagerFactory
			        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManager.init(trustStore);
			
			final TrustStrategy ts = (chain, authType) -> true;
			//
			sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, keystorePass.toCharArray())
			        .loadTrustMaterial(trustStore, ts).build();
			
		} catch (final Exception e) {
			LOGGER.error(MessageUtils.printStackTrace(e));
			throw new SSLContextSetupException(MessageUtils.printStackTrace(e));
		}

	}

	public SSLContext getSslContext() {
		return sslContext;
	}
	
}
