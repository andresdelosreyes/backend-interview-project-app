package com.ninjaone.backendinterviewproject.api.controller;

import com.ninjaone.backendinterviewproject.api.request.CostOfDevicesAndServicesRequest;
import com.ninjaone.backendinterviewproject.api.response.CostOfDeviceAndServicesResponse;
import com.ninjaone.backendinterviewproject.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cost")
public class CostController {

    @Autowired
    CostService costService;

    @PostMapping()
    private ResponseEntity<CostOfDeviceAndServicesResponse> getCostsOfDevicesAndServices(@RequestBody CostOfDevicesAndServicesRequest request) {
        CostOfDeviceAndServicesResponse response = costService.getCostOfDevicesAndServices(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
