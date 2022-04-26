package com.ninjaone.backendinterviewproject.database.repository;

import com.ninjaone.backendinterviewproject.database.model.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Long> {

    List<Device> findByActive(Boolean active);
}
