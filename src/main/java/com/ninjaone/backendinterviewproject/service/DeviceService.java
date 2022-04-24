package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.api.request.CreateDeviceRequest;
import com.ninjaone.backendinterviewproject.database.model.ActiveEnum;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.repository.DeviceRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Set<Device> getAll() {
        Set<Device> devices = deviceRepository.findByActive(ActiveEnum.YES.getValue());
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
        validateDeviceRequest(createDeviceRequest);
        return deviceRepository.save(createDeviceRequest.convertToNewDevice());
    }

    private void validateDeviceRequest(CreateDeviceRequest createDeviceRequest) {
        if (createDeviceRequest == null) {
            throw new IllegalArgumentException("Request is null");
        }
        if (createDeviceRequest.getDevice() == null) {
            throw new IllegalArgumentException("Device is null");
        }
        if (createDeviceRequest.getDeviceCost() == null) {
            throw new IllegalArgumentException("Device cannot have a null cost");
        }
        Set<Device> existingDevices = getAll().stream().filter(device -> device.getSystemName().equals(createDeviceRequest.getDevice().getSystemName()) && device.getType().equals(createDeviceRequest.getDevice().getType())).collect(Collectors.toSet());
        if (!existingDevices.isEmpty()) {
            throw new IllegalArgumentException("Device already exists");
        }
    }
}
