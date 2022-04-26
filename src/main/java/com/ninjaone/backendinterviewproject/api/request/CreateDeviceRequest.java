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

    public void validateForCreate() {
        if (device == null) {
            throw new IllegalArgumentException("Device is null");
        }
        device.validateForCreate();
        if (deviceCost == null) {
            throw new IllegalArgumentException("Device cannot have a null cost");
        }
    }

    public void validateForUpdate() {
        validateForCreate();
        device.validateForUpdate();
    }
}
