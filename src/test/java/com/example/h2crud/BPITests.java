package com.example.h2crud;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

// import com.example.h2crud.model.dto.BPIResponse.CurrencyDetails;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BPITests {
    @Autowired
    private MockMvc mockMvc;

    // @Autowired
    // private DateTimeFormatter dateTimeFormatter;

    // @Autowired
    // private ObjectMapper objectMapper;

    @Test
    public void GET_TranslatedBPI_Expected() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.timeUpdated").exists())
            .andExpect(jsonPath("$.bpi").exists());
        
            // .andExpect(result -> {
            //     String resultString = result.getResponse().getContentAsString();
            //     JsonNode rootNode = objectMapper.readTree(resultString);
            //     String timeUpdated = rootNode.path("timeUpdated").asText();
            //     try {
            //         ZonedDateTime.parse(timeUpdated, dateTimeFormatter);
            //     } catch (Exception e) {
            //         throw new AssertionError("Time format does not match expected pattern: ", e);
            //     }
            //     Map<String, CurrencyDetails> bpi = 
            // });
            
        MvcResult mvcResult = result.andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("Response content: " + responseContent);
    }
}
