package com.ninjaone.backendinterviewproject.api.request;

import java.util.ArrayList;
import java.util.List;

public class CostOfDevicesAndServicesRequest {

    private List<CostOfDeviceRequest> devices;

    private List<Long> serviceIds;

    public CostOfDevicesAndServicesRequest() {
    }

    public CostOfDevicesAndServicesRequest(List<CostOfDeviceRequest> devices, List<Long> serviceIds) {
        this.devices = devices;
        this.serviceIds = serviceIds;
    }

    public List<CostOfDeviceRequest> getDevices() {
        return devices;
    }

    public void setDevices(List<CostOfDeviceRequest> devices) {
        this.devices = devices;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public void validate() {
        if (devices == null) {
            throw new IllegalArgumentException("List of devices is null");
        }
        if (devices.isEmpty()) {
            throw new IllegalArgumentException("List of devices is empty");
        }
        devices.forEach(device -> device.validate());
        if (serviceIds == null) {
            serviceIds = new ArrayList<>();
        }
    }
}
