package com.ninjaone.backendinterviewproject.api.response;

import com.ninjaone.backendinterviewproject.database.model.Cost;
import com.ninjaone.backendinterviewproject.database.model.Service;

import java.io.Serializable;

public class CostResponse implements Serializable {

    private Double value;

    private Service service;

    public CostResponse() {
    }

    public CostResponse(Double value, Service service) {
        this.value = value;
        this.service = service;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    static CostResponse fromCost(Cost cost) {
        return new CostResponse(cost.getValue(), cost.getService());
    }
}
