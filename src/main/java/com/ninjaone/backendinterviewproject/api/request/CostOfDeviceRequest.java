package com.ninjaone.backendinterviewproject.api.request;

public class CostOfDeviceRequest {

    private Long deviceId;

    private Integer numberOfDevices;

    public CostOfDeviceRequest() {
    }

    public CostOfDeviceRequest(Long deviceId, Integer numberOfDevices) {
        this.deviceId = deviceId;
        this.numberOfDevices = numberOfDevices;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getNumberOfDevices() {
        return numberOfDevices;
    }

    public void setNumberOfDevices(Integer numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }

    public void validate() {
        if (deviceId == null) {
            throw new IllegalArgumentException("Devices id is null");
        }
        if (numberOfDevices == null) {
            throw new IllegalArgumentException("Number of devices is null");
        }
        if (numberOfDevices < 1) {
            throw new IllegalArgumentException("Number of devices must be >= 1");
        }
    }
}
