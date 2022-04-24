package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.model.ActiveEnum;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.repository.DeviceRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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
}
