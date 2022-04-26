package com.ninjaone.backendinterviewproject.api.request;

import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;

import java.io.Serializable;

public class DeviceRequest implements Serializable {

    private Long id;

    private String systemName;

    private DeviceTypeEnum type;

    public DeviceRequest() {
    }

    public DeviceRequest(String systemName, DeviceTypeEnum type) {
        this.systemName = systemName;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public DeviceTypeEnum getType() {
        return type;
    }

    public void setType(DeviceTypeEnum type) {
        this.type = type;
    }

    public Device toDevice() {
        return new Device(systemName, type);
    }

    public static DeviceRequest fromDevice(Device device) {
        return new DeviceRequest(device.getSystemName(), device.getType());
    }

    public void validateForCreate() {
        if (systemName == null) {
            throw new IllegalArgumentException("Device system name is null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Device type is null");
        }
    }

    public void validateForUpdate() {
        if (id == null) {
            throw new IllegalArgumentException("Device id is null");
        }
    }
}
