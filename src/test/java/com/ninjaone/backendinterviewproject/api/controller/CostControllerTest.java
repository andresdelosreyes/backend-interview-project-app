package com.ninjaone.backendinterviewproject.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.api.request.CostOfDeviceRequest;
import com.ninjaone.backendinterviewproject.api.request.CostOfDevicesAndServicesRequest;
import com.ninjaone.backendinterviewproject.api.response.CostDetail;
import com.ninjaone.backendinterviewproject.api.response.CostOfDeviceAndServicesResponse;
import com.ninjaone.backendinterviewproject.service.CostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = BackendInterviewProjectApplication.class)
@WebMvcTest(CostController.class)
class CostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CostService costService;

    @Test
    void shouldReturnTheExpectedCostResult() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> serviceIds = new ArrayList<>();
        serviceIds.add(1L);
        serviceIds.add(2L);
        List<CostOfDeviceRequest> devices = new ArrayList<>();
        devices.add(new CostOfDeviceRequest(1L, 2));
        CostOfDevicesAndServicesRequest request = new CostOfDevicesAndServicesRequest(devices, serviceIds);
        String jsonRequest = ow.writeValueAsString(request);
        List<CostDetail> costDetails = new ArrayList<>();
        costDetails.add(new CostDetail("desc", 22.2));
        CostOfDeviceAndServicesResponse expectedResponse = new CostOfDeviceAndServicesResponse(costDetails, 22.2);
        when(costService.getCostOfDevicesAndServices(any())).thenReturn(expectedResponse);

        MvcResult result = mvc.perform(post("/cost").content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        CostOfDeviceAndServicesResponse actualResponse = objectMapper.readValue(jsonResponse, CostOfDeviceAndServicesResponse.class);

        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}