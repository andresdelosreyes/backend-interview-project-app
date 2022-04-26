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
        /*
        TODO
        agregar un numeroDeDispositivos en el request, y multiplicar el valor por el numero de dispositivos en los costos
         */
        request.validate();
        List<Cost> costOfDevices = costRepository.findByDevice_idInAndServiceIsNull(request.getDevices().stream().map(device -> device.getDeviceId()).collect(Collectors.toList()));
        CostDetail detailedCostOfDevices = mapToCostDetail(costOfDevices, "Devices cost:", request.getDevices());

        CostOfDeviceAndServicesResponse response = new CostOfDeviceAndServicesResponse();

        response.addCostDetail(detailedCostOfDevices);


        if (!request.getServiceIds().isEmpty()) {
            for (Long serviceId : request.getServiceIds()) {

                List<Long> deviceIds = request.getDevices().stream().map(device -> device.getDeviceId()).collect(Collectors.toList());
                List<Cost> costsOfDevicesForService = costRepository.findByDevice_idInAndService_id(deviceIds, serviceId);
                String deviceDescription = costsOfDevicesForService.stream().findFirst().get().getService().getName();
                CostDetail costDetailOfDevicesWithService = mapToCostDetail(costsOfDevicesForService, deviceDescription, request.getDevices());
                response.addCostDetail(costDetailOfDevicesWithService);
            }
        }
        return response;
    }

    private CostDetail mapToCostDetail(List<Cost> costs, String description, List<CostOfDeviceRequest> costOfDeviceRequests) {
        Double totalCost = 0.0;
        Map<Long, Integer> numberOfDevices = costOfDeviceRequests.stream().collect(Collectors.toMap(CostOfDeviceRequest::getDeviceId, CostOfDeviceRequest::getNumberOfDevices));
        if (costs != null) {
            for (Cost cost : costs) {
                totalCost = totalCost + (numberOfDevices.get(cost.getDevice().getId()) * cost.getValue());
            }
            ;
        }
        return new CostDetail(description, totalCost);
    }
}
