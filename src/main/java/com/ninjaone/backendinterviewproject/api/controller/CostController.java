package com.ninjaone.backendinterviewproject.api.controller;

import com.ninjaone.backendinterviewproject.api.request.CostOfDeviceRequest;
import com.ninjaone.backendinterviewproject.api.request.CostOfDevicesAndServicesRequest;
import com.ninjaone.backendinterviewproject.api.response.CostOfDeviceAndServicesResponse;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/cost")
public class CostController {

    @Autowired
    CostService costService;

    @PostMapping()
    private ResponseEntity<CostOfDeviceAndServicesResponse> getCostsOfDevicesAndServices(@RequestBody CostOfDevicesAndServicesRequest request){
        CostOfDeviceAndServicesResponse response = costService.getCostOfDevicesAndServices(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
