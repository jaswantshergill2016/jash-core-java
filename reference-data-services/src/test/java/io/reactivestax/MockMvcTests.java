package io.reactivestax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.reactivestax.controller.ResourceTypeController;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.service.ResourceTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ResourceTypeController.class)
public class MockMvcTests {
    @MockBean
    private ResourceTypeService resourceTypeService;
    // @MockBean
    // private VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testPostResourceType() throws Exception {
        BDDMockito.given(resourceTypeService.createResourceType(ArgumentMatchers.anyObject())).willReturn(new ResourceTypes("Jaswant", "Jas", 10));

        Gson gson = new Gson();
        String resourceTypeJsonString = gson.toJson(new ResourceTypes("Jaswant", "Jas", 10));

//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resourceType").contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(resourceTypeJsonString))
//                .andExpect(status().isCreated())
//                .andDo(print())
//                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.status.statusDesc", is("OK")));
////
////                .andExpect(jsonPath("$.response.resourceName", is("Jaswant")))
////                .andExpect(jsonPath("$.response.resourceNickName", is("Jas")));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/resourceType")
                .content(asJsonString(new ResourceTypes("Jaswant", "Jas", 10)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status.statusDesc", is("OK")))
                .andExpect(jsonPath("$.response.resourceName", is("Jaswant")))
                .andExpect(jsonPath("$.response.resourceNickName", is("Jas")));


    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
