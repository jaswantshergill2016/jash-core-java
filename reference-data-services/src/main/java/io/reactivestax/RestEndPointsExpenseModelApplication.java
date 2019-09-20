package io.reactivestax;

//import com.reactivestax.domain.Vendors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("io.reactivestax")
@EnableDiscoveryClient
public class RestEndPointsExpenseModelApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestEndPointsExpenseModelApplication.class, args);
	}

//	@Bean
//	public Vendors getVendors(){
//		return new Vendors();
//	}
}
