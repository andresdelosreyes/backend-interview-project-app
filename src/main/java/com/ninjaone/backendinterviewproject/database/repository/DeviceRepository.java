package com.ninjaone.backendinterviewproject.database.repository;

import com.ninjaone.backendinterviewproject.database.model.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface DeviceRepository extends CrudRepository<Device, Long> {

    Set<Device> findByActive(Boolean active);
}
