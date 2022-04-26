package com.ninjaone.backendinterviewproject.database.repository;

import com.ninjaone.backendinterviewproject.database.model.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface DeviceRepository extends CrudRepository<Device, Long> {

    List<Device> findByActive(Boolean active);
}
