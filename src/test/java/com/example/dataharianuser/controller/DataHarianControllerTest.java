package com.example.dataharianuser.controller;

import com.example.dataharianuser.configuration.TokenValidationFilter;
import com.example.dataharianuser.model.DataHarian;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianRequest;
import com.example.dataharianuser.model.dto.dataHarian.DataHarianResponse;
import com.example.dataharianuser.model.dto.makanan.NutrisiDto;
import com.example.dataharianuser.service.dataHarian.DataHarianService;
import com.example.dataharianuser.service.utils.Authenticator;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(controllers = DataHarianController.class)
//@AutoConfigureMockMvc
class DataHarianControllerTest {
//    private MockMvc mvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @MockBean
//    private DataHarianService dataHarianService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private TokenValidationFilter tokenValidationFilter;
//
//    @InjectMocks
//    private DataHarianController dataHarianController;
//
//    Integer userId;
//    String bearerToken;
//    Date date;
//
//    List<DataHarianResponse> dataHarianResponses;
//    DataHarianResponse dataHarianResponse;
//
//    @BeforeEach
//    void setUp() {
//        Authenticator authenticator = mock(Authenticator.class);
//        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
//
//        userId = 1;
//        bearerToken = "Bearer token";
//        date = new Date();
//        dataHarianResponse = DataHarianResponse.builder()
//                .id(1L)
//                .date(date)
//                .targetKalori(2000.0)
//                .build();
//
//        dataHarianResponses = List.of(dataHarianResponse);
//    }
//
//    @Test
//    void testGetAllUserDataHarian() throws Exception {
//        when(dataHarianService.findAllByUserId(anyInt(), anyString())).thenReturn(dataHarianResponses);
//
//        // Create a MockHttpServletRequestBuilder with the filter parameters
//        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/data-harian/all")
//                .param("userId", userId.toString())
//                .header("Authorization", bearerToken)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        // Perform the request and assert the response
//        mvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(handler().methodName("getAllUserDataHarian"))
//                .andExpect(jsonPath("$[0].id").value(dataHarianResponse.getId()));
//
//        // Verify the service method was called with the correct filter parameters
//        verify(dataHarianService, atLeastOnce()).findAllByUserId(userId, bearerToken);
//    }

//    @Test
//    void testCreateDataHarian() throws Exception {
//        DataHarianRequest dataHarianRequest = new DataHarianRequest();
//        DataHarian expectedResponse = new DataHarian();
//        when(dataHarianService.create(anyInt(), any(DataHarianRequest.class))).thenReturn(expectedResponse);
//
//        mvc.perform(post("/api/v1/data-harian/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(...)) // Add the necessary request content
//                .andExpect(status().isOk())
//                .andExpect(handler().methodName("createDataHarian"))
//                .andExpect(jsonPath("$....").value(...)); // Add the necessary assertions for the response
//
//        verify(dataHarianService, atLeastOnce()).create(anyInt(), any(DataHarianRequest.class));
//    }

    // Add more test cases for other methods if needed
}
