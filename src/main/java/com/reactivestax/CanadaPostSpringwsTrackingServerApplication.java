package com.reactivestax;

//import ca.canadapost.ws.soap.track.GetTrackingDetailRequest;
//import ca.canadapost.ws.soap.track.GetTrackingDetailResponse;
//import ca.canadapost.ws.soap.track.ObjectFactory;
//import com.reactivestax.client.SOAPConnector;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CanadaPostSpringwsTrackingServerApplication implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(CanadaPostSpringwsTrackingServerApplication.class, args);

        System.out.println("=============testing local service ==============");
	}

    @Override
    public void run(String... args) throws Exception {

    }


}
