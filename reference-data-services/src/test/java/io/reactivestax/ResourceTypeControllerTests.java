package io.reactivestax;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.service.ResourceTypeService;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import static org.apache.http.HttpStatus.SC_CREATED;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestEndPointsExpenseModelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceTypeControllerTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ResourceTypeService resourceTypeService;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void test(){
        BDDMockito.given(resourceTypeService.createResourceType(ArgumentMatchers.anyObject())).willReturn(new ResourceTypes("Jaswant","Jas",10));

        Gson gson = new Gson();
        //String resourceTypeJsonString = gson.toJson(new ResourceTypes("Jaswant","Jas",10));

        //ClientMetaData clientMetaData = gson.fromJson("{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}", ClientMetaData.class);

        System.out.println("baseUrl = " + getRootUrl());
        Response response =

                RestAssured
                        .given()
                        //.header("client-metadata", clientMetaData)
                        .contentType(ContentType.JSON)
                        .request()
                        .body("{\n" +
                                "\"resourceName\": \"Jaswant\",\n" +
                                "\"resourceNickName\":\"Jas\",\n" +
                                "\"createdBy\":10\n" +
                                "}")
                        .when()
                        .post(getRootUrl()+"/api/v1/resourceType")
                        .then()
                        .statusCode(SC_CREATED)
                        .contentType(ContentType.JSON)
//						.assertThat()
//						.body(Matchers.notNull())
                        .extract().response();
        String responseString = response.asString();
        System.out.println("responseString:"+responseString);

        ResourceTypes resourceTypes = gson.fromJson(responseString, ResourceTypes.class);
        Assertions.assertThat(StringUtils.equalsIgnoreCase(resourceTypes.getResourceName(),"Jaswant"));
        Assertions.assertThat(StringUtils.equalsIgnoreCase(resourceTypes.getResourceNickName(),"Jas"));
        //Assertions.assertThat(StringUtils.equalsIgnoreCase(resourceTypes.getCreatedBy(),10));

    }

    @Test
    public void testGetResourceTypesById(){
        ResourceTypes resourceTypes= testRestTemplate.getForObject(getRootUrl()+"/api/v1/resourceType/3",ResourceTypes.class);
        System.out.println(getRootUrl()+"/api/v1/resourceType/3");
        System.out.println(resourceTypes);
        System.out.println(resourceTypes.getResourceName());
        Assert.assertNotNull(resourceTypes);
    }

    @Test
    public void testCreateResourceTypes(){
        ResourceTypes resourceTypes=new ResourceTypes();
        resourceTypes.setCreatedBy(0);
        resourceTypes.setResourceNickName("JASWANT SHERGILL");
        resourceTypes.setResourceName("JASWANT");
        resourceTypes.setResourceTypeId(2);

        ResponseEntity<ResourceTypes> postResponse = testRestTemplate.postForEntity(getRootUrl()+"/api/v1/resourceType",resourceTypes, ResourceTypes.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateResourceTypes(){
        int resourceTypeId =1;
        ResourceTypes resourceTypes = testRestTemplate.getForObject(getRootUrl() + "/api/v1/resourceType" + resourceTypeId, ResourceTypes.class);
        resourceTypes.setResourceName("food");
        resourceTypes.setResourceNickName("muffin");
        testRestTemplate.put(getRootUrl() + "/api/v1/resourceType" + resourceTypeId, resourceTypes);
        ResourceTypes updatedResourceTypes = testRestTemplate.getForObject(getRootUrl() + "/api/v1/resourceType" + resourceTypeId, ResourceTypes.class);
        Assert.assertNotNull(updatedResourceTypes);
    }

    @Test
    public void testDeleteResourceTypes(){
        int resourceTypeId =2;
        ResourceTypes resourceTypes = testRestTemplate.getForObject(getRootUrl() + "/api/v1/resourceType" + resourceTypeId, ResourceTypes.class);
        Assert.assertNotNull(resourceTypes);

        testRestTemplate.delete(getRootUrl()+"/api/v1/resourceType"+resourceTypeId);

        try{
            resourceTypes = testRestTemplate.getForObject(getRootUrl() + "/api/v1/resourceType" + resourceTypeId, ResourceTypes.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

    }
}
