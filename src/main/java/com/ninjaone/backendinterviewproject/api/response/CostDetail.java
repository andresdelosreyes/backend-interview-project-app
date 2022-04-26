package com.ninjaone.backendinterviewproject.api.response;

import com.ninjaone.backendinterviewproject.database.model.Cost;

import java.io.Serializable;
import java.util.Set;

public class CostDetail implements Serializable {

    private String description;

    private Double totalCost;

    public CostDetail() {
    }

    public CostDetail(String description, Double totalCost) {
        this.description = description;
        this.totalCost = totalCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
