package com.ninjaone.backendinterviewproject.api.response;

import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceResponse implements Serializable {
    private Long id;

    private String systemName;

    private DeviceTypeEnum type;

    private List<CostResponse> costs;

    public DeviceResponse() {
    }

    public DeviceResponse(Long id, String systemName, DeviceTypeEnum type, List<CostResponse> costs) {
        this.id = id;
        this.systemName = systemName;
        this.type = type;
        this.costs = costs;
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

    public List<CostResponse> getCosts() {
        return costs;
    }

    public void setCosts(List<CostResponse> costs) {
        this.costs = costs;
    }

    public static DeviceResponse fromDevice(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getSystemName(),
                device.getType(),
                device.getCosts().stream().map(cost -> CostResponse.fromCost(cost)).collect(Collectors.toList())
        );
    }
}
