package com.example.h2crud;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// import java.util.List;
// import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CurrencyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void Get_Currencies_Expected() throws Exception {
        int expectedNum = 3;
        ResultActions result = mockMvc.perform(get("/currencies"));
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(expectedNum)));
        
        for (int i = 0; i < expectedNum; i++) {
            String basePath = "$["+i+"]";
            result
                .andExpect(jsonPath(basePath, notNullValue()))
                .andExpect(jsonPath(basePath+".id", notNullValue()))
                .andExpect(jsonPath(basePath+".code", notNullValue()))
                .andExpect(jsonPath(basePath+".chineseName", notNullValue()));
        }
        //result.andDo(print());

        MvcResult mvcResult = result.andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("Response content: " + responseContent);
    }

    @Test
    @Order(2)
    public void Add_Currencies_Created() throws Exception {
        // // 1. bad requests
        // List<String> badRequestBodies = Arrays.asList(
        //     "{\"name\": \"錯誤\"}",                         // missing "code"
        //     "{\"code\": \"JPY\"}",                              // missing "name"
        //     "{\"code\": \"ERR\", \"chineseName\": \"錯誤\"}",   // wrong attribute "name"
        //     "{\"code\": \"ABCD\", \"name\": \"過長\"}"         // code too long
        // );
        // for (String badRequestBody : badRequestBodies) {
        //     mockMvc.perform(post("/currencies")
        //             .contentType(MediaType.APPLICATION_JSON)
        //             .content(badRequestBody))
        //             .andExpect(status().isBadRequest());
        // }
        
        // // 2. conflict
        // String requestBodyUSD = "{\"code\": \"USD\", \"name\": \"美元\"}"; // existing "code"
        // mockMvc.perform(post("/currencies")
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .content(requestBodyUSD))
        //     .andExpect(status().isConflict());
        
        // 3. created
        String requestBodyJPY = "{\"code\":\"JPY\",\"name\":\"日圓\"}";
        mockMvc.perform(post("/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJPY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code").value("JPY"))
                .andExpect(jsonPath("$.chineseName").value("日圓"));
    }

    @Test
    @Order(3)
    public void Get_Currency_Expected() throws Exception {
        mockMvc.perform(get("/currencies/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code").value("GBP"))
                .andExpect(jsonPath("$.chineseName").value("英鎊"));
    }

    @Test
    @Order(4)
    public void Update_Currency_Expected() throws Exception {
        String requestBody = "{\"code\":\"GBP\",\"name\":\"英磅\"}";
        ResultActions result = mockMvc.perform(put("/currencies/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code").value("GBP"))
                .andExpect(jsonPath("$.chineseName").value("英磅"));
        MvcResult mvcResult = result.andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("Response content: " + responseContent);
    }

    @Test
    @Order(5)
    public void Delete_Currency_Expected() throws Exception {
        mockMvc.perform(delete("/currencies/2"))
            .andExpect(status().isOk());
    }
}
