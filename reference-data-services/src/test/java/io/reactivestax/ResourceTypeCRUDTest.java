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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
@SpringBootTest(classes = ResourceTypes.class)
@Slf4j
public class ResourceTypeCRUDTest {
    //    @LocalServerPort
//    private int port;
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    private String uri = "http://localhost:8181/api/v1/resourceType";
    private String restResponse;

    @Test
    public void createResourceType() {

        ResourceTypes resourceTypes = new ResourceTypes();

        resourceTypes.setResourceName("Jaswant");
        resourceTypes.setResourceNickName("Jas");
        resourceTypes.setCreatedBy(10);


        HttpEntity<ResourceTypes> entity = new HttpEntity<>(resourceTypes, headers);
        //ResponseEntity<String> responseEntity =
              //  testRestTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        ResponseEntity<String> responseEntity =
                testRestTemplate.postForEntity(uri,  entity, String.class);
        restResponse = responseEntity.getBody();
        try {
            RestApiResponse<RestApiStatus, ResourceTypes> postApiResponse =
                    deSerialize(restResponse, new TypeReference<RestApiResponse<RestApiStatus, ResourceTypes>>() {
                    });
            assert postApiResponse.getResponse().getResourceName().equals(resourceTypes.getResourceName());
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public <T> T deSerialize(String inStream, TypeReference<T> typeReference)  throws IOException {
        JavaTimeModule module = new JavaTimeModule();

        LocalDateTimeDeserializer localDateTimeDeserializer = new
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        return objectMapper.readValue(inStream, typeReference);
    }
}