package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.api.request.CreateDeviceRequest;
import com.ninjaone.backendinterviewproject.database.model.ActiveEnum;
import com.ninjaone.backendinterviewproject.database.model.Cost;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.repository.DeviceRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAll() {
        List<Device> devices = deviceRepository.findByActive(ActiveEnum.YES.getValue());
        return devices;
    }

    public Device getById(Long deviceId) {
        Optional<Device> resultingDevice = deviceRepository.findById(deviceId);
        if (resultingDevice.isEmpty()) {
            throw new ObjectNotFoundException(deviceId, "Device");
        }
        return resultingDevice.get();
    }

    public Device create(CreateDeviceRequest createDeviceRequest) {
        validateCreateDeviceRequest(createDeviceRequest);
        return deviceRepository.save(createDeviceRequest.convertToNewDevice());
    }

    public Device update(CreateDeviceRequest updateDeviceRequest) {
        validateUpdateDeviceRequest(updateDeviceRequest);
        return findExistingDeviceAndUpdate(updateDeviceRequest);
    }

    public void delete(Long deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    private void validateCreateDeviceRequest(CreateDeviceRequest createDeviceRequest) {
        CreateDeviceRequest.validateForCreate(createDeviceRequest);
        List<Device> existingDevices = getAll().stream().filter(device -> createDeviceRequest.getDevice().getSystemName().equals(device.getSystemName()) && createDeviceRequest.getDevice().getType().equals(device.getType())).collect(Collectors.toList());
        if (!existingDevices.isEmpty()) {
            throw new IllegalArgumentException("Device already exists");
        }
    }

    private void validateUpdateDeviceRequest(CreateDeviceRequest updateDeviceRequest) {
        CreateDeviceRequest.validateForUpdate(updateDeviceRequest);
    }

    private Device findExistingDeviceAndUpdate(CreateDeviceRequest updateDeviceRequest) {
        Device existingDevice = getById(updateDeviceRequest.getDevice().getId());
        Cost costOfDevice = existingDevice.getCosts().stream().filter(cost -> cost.getService() == null).findFirst().get();
        costOfDevice.setValue(updateDeviceRequest.getDeviceCost());
        existingDevice.setSystemName(updateDeviceRequest.getDevice().getSystemName());
        existingDevice.setType(updateDeviceRequest.getDevice().getType());
        return deviceRepository.save(existingDevice);
    }
}
