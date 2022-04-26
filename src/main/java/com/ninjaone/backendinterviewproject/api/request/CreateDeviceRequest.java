package com.ninjaone.backendinterviewproject.api.request;

import com.ninjaone.backendinterviewproject.database.model.Cost;
import com.ninjaone.backendinterviewproject.database.model.Device;

import java.io.Serializable;

public class CreateDeviceRequest implements Serializable {

    private DeviceRequest device;

    private Double deviceCost;

    public CreateDeviceRequest(){
    }

    public DeviceRequest getDevice() {
        return device;
    }

    public void setDevice(DeviceRequest device) {
        this.device = device;
    }

    public Double getDeviceCost() {
        return deviceCost;
    }

    public void setDeviceCost(Double deviceCost) {
        this.deviceCost = deviceCost;
    }

    public Device convertToNewDevice() {
        Device result = device.toDevice();
        Cost cost = new Cost(result, deviceCost, null);
        result.getCosts().add(cost);
        return result;
    }

    public static void validateForCreate(CreateDeviceRequest createDeviceRequest) {
        if (createDeviceRequest == null) {
            throw new IllegalArgumentException("Request is null");
        }
        if (createDeviceRequest.getDevice() == null) {
            throw new IllegalArgumentException("Device is null");
        }
        createDeviceRequest.getDevice().validateForCreate();
        if (createDeviceRequest.getDeviceCost() == null) {
            throw new IllegalArgumentException("Device cannot have a null cost");
        }
    }

    public static void validateForUpdate(CreateDeviceRequest updateDeviceRequest) {
        CreateDeviceRequest.validateForCreate(updateDeviceRequest);
        updateDeviceRequest.getDevice().validateForUpdate();
    }
}
