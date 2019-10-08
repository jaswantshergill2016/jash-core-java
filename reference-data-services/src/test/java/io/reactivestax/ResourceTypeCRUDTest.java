package io.reactivestax;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.model.RestApiResponse;
import io.reactivestax.model.RestApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = RestEndPointsExpenseModelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(classes = ResourceTypes.class)
@Slf4j
public class ResourceTypeCRUDTest {
    //    @LocalServerPort
//    private int port;
    //@LocalServerPort
    private int port;

    private int portt = 8181;

    TestRestTemplate testRestTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    private String url = "http://localhost:8181/api/v1/resourceType";
    private String restResponse;

    private String createURLWithPort(String uri) {
        String URL = "http://localhost:" + portt + uri;
        System.out.println("URL ===>   "+URL);
        System.out.println("LocalServerPort ===>   "+port);
        return URL;
    }

    @Test
    public void createResourceType() {

        ResourceTypes resourceTypes = new ResourceTypes();

        resourceTypes.setResourceName("Jaswant");
        resourceTypes.setResourceNickName("Jas");
        resourceTypes.setCreatedBy(10);


        HttpEntity<ResourceTypes> entity = new HttpEntity<>(resourceTypes, headers);
       // ResponseEntity<String> responseEntity =
         //     testRestTemplate.exchange(createURLWithPort("/api/v1/resourceType"), HttpMethod.POST, entity, String.class);
        ResponseEntity<String> responseEntity =
                testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
       // ResponseEntity<String> responseEntity =
           //     testRestTemplate.postForEntity(uri,  entity, String.class);
        restResponse = responseEntity.getBody();
        try {
            RestApiResponse<RestApiStatus, ResourceTypes> postApiResponse =
                    deSerialize(restResponse, new TypeReference<RestApiResponse<RestApiStatus, ResourceTypes>>() {
                    });
            System.out.println("===> "+postApiResponse.getStatus().getStatusCode());
            System.out.println("===> "+postApiResponse.getResponse());
            System.out.println("===> "+postApiResponse.getResponse().getResourceName());
            assert postApiResponse.getResponse().getResourceName().equals(resourceTypes.getResourceName());
        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Test
    public void testPostResourceType() {

        ResourceTypes resourceTypes = new ResourceTypes();

        resourceTypes.setResourceName("Jaswant");
        resourceTypes.setResourceNickName("Jas");
        resourceTypes.setCreatedBy(10);


        HttpEntity<ResourceTypes> entity = new HttpEntity<>(resourceTypes, headers);
        // ResponseEntity<String> responseEntity =
        //     testRestTemplate.exchange(createURLWithPort("/api/v1/resourceType"), HttpMethod.POST, entity, String.class);
        ResponseEntity<ResourceTypes> responseEntity =
                testRestTemplate.exchange(url, HttpMethod.POST, entity, ResourceTypes.class);
        // ResponseEntity<String> responseEntity =
        //     testRestTemplate.postForEntity(uri,  entity, String.class);
        ResourceTypes restResponse = responseEntity.getBody();
        //try {
           // RestApiResponse<RestApiStatus, ResourceTypes> postApiResponse =
             //       deSerialize(restResponse, new TypeReference<RestApiResponse<RestApiStatus, ResourceTypes>>() {
               //     });
            System.out.println("===> "+restResponse.getResourceName());
           // System.out.println("===> "+postApiResponse.getResponse());
            //System.out.println("===> "+postApiResponse.getResponse().getResourceName());
            assert restResponse.getResourceName().equals(resourceTypes.getResourceName());
//        } catch (IOException e) {
//            e.getMessage();
//            e.printStackTrace();
//        }
    }

    @Test
    public void testCreateResourceTypes(){
        ResourceTypes resourceTypes=new ResourceTypes();
        resourceTypes.setCreatedBy(10);
        resourceTypes.setResourceNickName("Jas");
        resourceTypes.setResourceName("Jaswant");
        resourceTypes.setResourceTypeId(12);

        ResponseEntity<ResourceTypes> postResponse =testRestTemplate.postForEntity(url,resourceTypes, ResourceTypes.class);
        System.out.println(postResponse);
        System.out.println(postResponse.getBody());
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }

    public <T> T deSerialize(String inStream, TypeReference<T> typeReference)  throws IOException {
        System.out.println("inStream ===>  "+inStream);
        JavaTimeModule module = new JavaTimeModule();

        LocalDateTimeDeserializer localDateTimeDeserializer = new
               // LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));

        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        return objectMapper.readValue(inStream, typeReference);
    }
}