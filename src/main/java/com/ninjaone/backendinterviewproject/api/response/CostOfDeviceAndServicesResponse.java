package com.ninjaone.backendinterviewproject.api.response;

import java.util.ArrayList;
import java.util.List;

public class CostOfDeviceAndServicesResponse {

    private List<CostDetail> costDetails;

    private Double totalCost;

    public CostOfDeviceAndServicesResponse() {
        this.totalCost = 0.0;
        this.costDetails = new ArrayList<>();
    }

    public CostOfDeviceAndServicesResponse(List<CostDetail> costDetails, Double totalCost) {
        this.costDetails = costDetails;
        this.totalCost = totalCost;
    }

    public List<CostDetail> getCostDetails() {
        return costDetails;
    }

    public void setCostDetails(List<CostDetail> costDetails) {
        this.costDetails = costDetails;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void addCostDetail(CostDetail costDetail) {
        this.costDetails.add(costDetail);
        this.totalCost += costDetail.getTotalCost();
    }
}
