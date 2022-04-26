package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.api.request.CostOfDeviceRequest;
import com.ninjaone.backendinterviewproject.api.request.CostOfDevicesAndServicesRequest;
import com.ninjaone.backendinterviewproject.api.response.CostDetail;
import com.ninjaone.backendinterviewproject.api.response.CostOfDeviceAndServicesResponse;
import com.ninjaone.backendinterviewproject.database.model.Cost;
import com.ninjaone.backendinterviewproject.database.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CostService {

    @Autowired
    CostRepository costRepository;

    public CostOfDeviceAndServicesResponse getCostOfDevicesAndServices(CostOfDevicesAndServicesRequest request) {
        request.validate();
        CostOfDeviceAndServicesResponse response = new CostOfDeviceAndServicesResponse();
        // first adds the cost of devices without services
        response.addCostDetail(getCostOfDevices(request.getDevices()));
        if (!request.getServiceIds().isEmpty()) {
            request.getServiceIds().forEach(
                    serviceId -> response.addCostDetail(getCostOfDevicesPerService(request.getDevices(), serviceId))
            );
        }
        return response;
    }

    private CostDetail getCostOfDevices(List<CostOfDeviceRequest> devices) {
        List<Long> deviceIds = devices.stream().map(device -> device.getDeviceId()).collect(Collectors.toList());
        List<Cost> costOfDevices = costRepository.findByDevice_idInAndServiceIsNull(deviceIds);
        return mapToCostDetail(costOfDevices, "Devices", devices);
    }

    private CostDetail getCostOfDevicesPerService(List<CostOfDeviceRequest> devices, Long serviceId) {
        List<Long> deviceIds = devices.stream().map(device -> device.getDeviceId()).collect(Collectors.toList());
        List<Cost> costsOfDevicesPerService = costRepository.findByDevice_idInAndService_id(deviceIds, serviceId);
        // all services in the list of costs are the same
        String deviceDescription = costsOfDevicesPerService.stream().findFirst().get().getService().getName();
        return mapToCostDetail(costsOfDevicesPerService, deviceDescription, devices);
    }

    private CostDetail mapToCostDetail(List<Cost> costs, String description, List<CostOfDeviceRequest> costOfDeviceRequests) {
        Double totalCost = 0.0;
        Map<Long, Integer> numberOfDevices = costOfDeviceRequests.stream().collect(Collectors.toMap(CostOfDeviceRequest::getDeviceId, CostOfDeviceRequest::getNumberOfDevices));
        if (costs != null) {
            for (Cost cost : costs) {
                totalCost = totalCost + (numberOfDevices.get(cost.getDevice().getId()) * cost.getValue());
            }
        }
        return new CostDetail(description + " cost:", totalCost);
    }
}
