package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.api.request.CreateDeviceRequest;
import com.ninjaone.backendinterviewproject.api.request.DeviceRequest;
import com.ninjaone.backendinterviewproject.database.model.ActiveEnum;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;
import com.ninjaone.backendinterviewproject.database.repository.DeviceRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void shouldCallDeviceRepositoryFindByActive() {
        when(deviceRepository.findByActive(ActiveEnum.YES.getValue())).thenReturn(new HashSet<>());

        deviceService.getAll();

        verify(deviceRepository).findByActive(argThat(active -> ActiveEnum.YES.getValue()));
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenTheGivenIdDoesNotExists() {
        Long nonExistingId = 10L;
        String expectedMessage = "No row with the given identifier exists: [Device#" + nonExistingId + "]";
        when(deviceRepository.findById(nonExistingId)).thenReturn(Optional.ofNullable(null));

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            deviceService.getById(nonExistingId);
        });

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void shouldCallDeviceRepositoryFindByIdWithTheGivenId() {
        Long givenId = 10L;
        when(deviceRepository.findById(givenId)).thenReturn(Optional.of(new Device()));

        deviceService.getById(givenId);

        verify(deviceRepository).findById(argThat(id -> id.equals(givenId)));
    }

    @Test void shouldThrowAnIllegalArgumentExceptionWhenCreateDeviceRequestIsNull() {
        CreateDeviceRequest nullCreateDeviceRequest = null;
        String expectedMessage = "Request is null";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deviceService.create(nullCreateDeviceRequest);
        });

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test void shouldThrowAnIllegalArgumentExceptionWhenCreateDeviceRequestHasNullDeviceRequest() {
        DeviceRequest nullDevice = null;
        Double cost = Double.valueOf("123");
        CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
        createDeviceRequest.setDevice(nullDevice);
        createDeviceRequest.setDeviceCost(cost);
        String expectedMessage = "Device is null";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deviceService.create(createDeviceRequest);
        });

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test void shouldThrowAnIllegalArgumentExceptionWhenCreateDeviceRequestHasNullCost() {
        Double nullCost = null;
        DeviceRequest deviceRequest = new DeviceRequest();
        CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
        createDeviceRequest.setDevice(deviceRequest);
        createDeviceRequest.setDeviceCost(nullCost);
        String expectedMessage = "Device cannot have a null cost";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deviceService.create(createDeviceRequest);
        });

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test void shouldThrowAnIllegalArgumentExceptionWhenDeviceAlreadyExists() {
        String existingSystemName = "Test System Name";
        DeviceTypeEnum existingType = DeviceTypeEnum.WINDOWS_SERVER;
        Double cost = Double.valueOf("123");
        Device existingDevice = new Device(existingSystemName, existingType);
        CreateDeviceRequest request = new CreateDeviceRequest();
        DeviceRequest exisingDeviceRequest = new DeviceRequest(existingSystemName, existingType);
        request.setDevice(exisingDeviceRequest);
        request.setDeviceCost(cost);
        String expectedMessage = "Device already exists";
        when(deviceRepository.findByActive(ActiveEnum.YES.getValue())).thenReturn(Stream.of(existingDevice).collect(Collectors.toSet()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deviceService.create(request);
        });

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test void shouldReturnTheResultOfTheSaveMethodWhenGivenAValidCreateRequest() {
        String systemName = "Test System Name";
        DeviceTypeEnum type = DeviceTypeEnum.WINDOWS_SERVER;
        Double cost = Double.valueOf("123");
        Device expectedDevice = new Device(systemName, type);
        CreateDeviceRequest request = new CreateDeviceRequest();
        DeviceRequest deviceRequest = new DeviceRequest(systemName, type);
        request.setDevice(deviceRequest);
        request.setDeviceCost(cost);
        when(deviceRepository.findByActive(ActiveEnum.YES.getValue())).thenReturn(new HashSet<>());
        when(deviceRepository.save(any())).thenReturn(expectedDevice);

        Device actualResult = deviceService.create(request);

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedDevice);
    }
}