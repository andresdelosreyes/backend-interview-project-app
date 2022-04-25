package com.ninjaone.backendinterviewproject.api.controller;

import com.ninjaone.backendinterviewproject.api.request.CreateDeviceRequest;
import com.ninjaone.backendinterviewproject.api.response.DeviceResponse;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @GetMapping()
    private Device getDeviceById(@RequestParam Long deviceId){
        return deviceService.getById(deviceId);
    }

    @PostMapping()
    private ResponseEntity<DeviceResponse> createDevice(@RequestBody CreateDeviceRequest newDeviceRequest) {
        return new ResponseEntity<>(DeviceResponse.fromDevice(deviceService.create(newDeviceRequest)), HttpStatus.CREATED);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<String> handleObjectNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Device not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<String> handleIllegalArgumentException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
