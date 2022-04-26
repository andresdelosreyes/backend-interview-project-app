package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.api.request.CreateDeviceRequest;
import com.ninjaone.backendinterviewproject.api.request.DeviceRequest;
import com.ninjaone.backendinterviewproject.database.model.ActiveEnum;
import com.ninjaone.backendinterviewproject.database.model.Cost;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;
import com.ninjaone.backendinterviewproject.database.model.Service;
import com.ninjaone.backendinterviewproject.database.repository.DeviceRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Nested
    class GetAll {
        @Test
        void shouldCallDeviceRepositoryFindByActive() {
            when(deviceRepository.findByActive(ActiveEnum.YES.getValue())).thenReturn(new ArrayList<>());

            deviceService.getAll();

            verify(deviceRepository).findByActive(argThat(active -> ActiveEnum.YES.getValue()));
        }
    }

    @Nested
    class GetById {
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

        @Test
        void shouldReturnTheListOfDevicesReturnedByTheRepository() {
            Device expectedDevice = new Device();
            Long id = 10L;
            when(deviceRepository.findById(id)).thenReturn(Optional.of(expectedDevice));

            Device actualDevice = deviceService.getById(id);

            assertThat(actualDevice).usingRecursiveComparison().isEqualTo(expectedDevice);
        }
    }

    @Nested
    class Create {
        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenCreateDeviceRequestIsNull() {
            CreateDeviceRequest nullCreateDeviceRequest = null;
            String expectedMessage = "Request is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceService.create(nullCreateDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenCreateDeviceRequestHasNullDeviceRequest() {
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

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenCreateDeviceRequestHasNullCost() {
            Double nullCost = null;
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setType(DeviceTypeEnum.WINDOWS_SERVER);
            deviceRequest.setSystemName("Test System Name");
            CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
            createDeviceRequest.setDevice(deviceRequest);
            createDeviceRequest.setDeviceCost(nullCost);
            String expectedMessage = "Device cannot have a null cost";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceService.create(createDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenDeviceAlreadyExists() {
            String existingSystemName = "Test System Name";
            DeviceTypeEnum existingType = DeviceTypeEnum.WINDOWS_SERVER;
            Double cost = Double.valueOf("123");
            Device existingDevice = new Device(existingSystemName, existingType);
            CreateDeviceRequest request = new CreateDeviceRequest();
            DeviceRequest exisingDeviceRequest = new DeviceRequest(existingSystemName, existingType);
            request.setDevice(exisingDeviceRequest);
            request.setDeviceCost(cost);
            String expectedMessage = "Device already exists";
            when(deviceRepository.findByActive(ActiveEnum.YES.getValue())).thenReturn(Stream.of(existingDevice).collect(Collectors.toList()));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceService.create(request);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldReturnTheResultOfTheSaveMethodWhenGivenAValidCreateRequest() {
            String systemName = "Test System Name";
            DeviceTypeEnum type = DeviceTypeEnum.WINDOWS_SERVER;
            Double cost = Double.valueOf("123");
            Device expectedDevice = new Device(systemName, type);
            Device existingDevice = new Device();
            CreateDeviceRequest request = new CreateDeviceRequest();
            DeviceRequest deviceRequest = new DeviceRequest(systemName, type);
            request.setDevice(deviceRequest);
            request.setDeviceCost(cost);
            when(deviceRepository.findByActive(ActiveEnum.YES.getValue())).thenReturn(Stream.of(existingDevice).collect(Collectors.toList()));
            when(deviceRepository.save(any())).thenReturn(expectedDevice);

            Device actualResult = deviceService.create(request);

            assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedDevice);
        }
    }

    @Nested
    class Update {
        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestIsNull() {
            CreateDeviceRequest nullUpdateDeviceRequest = null;
            String expectedMessage = "Request is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceService.update(nullUpdateDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestHasNullDeviceRequest() {
            DeviceRequest nullDevice = null;
            Double cost = Double.valueOf("123");
            CreateDeviceRequest updateDeviceRequest = new CreateDeviceRequest();
            updateDeviceRequest.setDevice(nullDevice);
            updateDeviceRequest.setDeviceCost(cost);
            String expectedMessage = "Device is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceService.update(updateDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestHasNullCost() {
            Double nullCost = null;
            DeviceRequest deviceRequest = new DeviceRequest();
            CreateDeviceRequest updateDeviceRequest = new CreateDeviceRequest();
            updateDeviceRequest.setDevice(deviceRequest);
            updateDeviceRequest.setDeviceCost(nullCost);
            String expectedMessage = "Device system name is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceService.update(updateDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestHasNullId() {
            Double cost = Double.valueOf("123");
            Long deviceId = null;
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setSystemName("System Name");
            deviceRequest.setType(DeviceTypeEnum.MAC);
            deviceRequest.setId(deviceId);
            CreateDeviceRequest updateDeviceRequest = new CreateDeviceRequest();
            updateDeviceRequest.setDevice(deviceRequest);
            updateDeviceRequest.setDeviceCost(cost);
            String expectedMessage = "Device id is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceService.update(updateDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldReturnTheUpdatedDeviceWhenGivenDeviceExists() {
            Long deviceId = 1L;
            String existingSystemName = "Test System Name";
            String newSystemName = "Updated System Name";
            DeviceTypeEnum existingType = DeviceTypeEnum.WINDOWS_SERVER;
            DeviceTypeEnum newType = DeviceTypeEnum.MAC;
            Double previousCostOfDeviceValue = Double.valueOf("123");
            Double newCostOfDeviceValue = Double.valueOf("234");
            Double costOfDeviceInAServiceValue = Double.valueOf("456");
            Device existingDevice = new Device(existingSystemName, existingType);
            Cost previousCostOfDevice = new Cost(existingDevice, previousCostOfDeviceValue, null);
            Cost costOfDeviceInAService = new Cost(existingDevice, costOfDeviceInAServiceValue, new Service());
            Cost newCostOfDevice = new Cost(existingDevice, newCostOfDeviceValue, null);
            List<Cost> existingCosts = Stream.of(previousCostOfDevice, costOfDeviceInAService).collect(Collectors.toList());
            List<Cost> expectedDeviceCosts = Stream.of(newCostOfDevice, costOfDeviceInAService).collect(Collectors.toList());
            existingDevice.setCosts(existingCosts);
            existingDevice.setId(deviceId);
            Device expectedDevice = new Device(newSystemName, newType);
            expectedDevice.setActive(ActiveEnum.YES.getValue());
            expectedDevice.setCosts(expectedDeviceCosts);
            expectedDevice.setId(deviceId);
            CreateDeviceRequest request = new CreateDeviceRequest();
            DeviceRequest updateDeviceRequest = new DeviceRequest(newSystemName, newType);
            updateDeviceRequest.setId(deviceId);
            request.setDevice(updateDeviceRequest);
            request.setDeviceCost(newCostOfDeviceValue);
            when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(existingDevice));
            when(deviceRepository.save(any())).thenReturn(expectedDevice);
            ArgumentCaptor<Device> deviceArgumentCaptor = ArgumentCaptor.forClass(Device.class);

            deviceService.update(request);

            verify(deviceRepository, times(1)).save(deviceArgumentCaptor.capture());
            assertThat(deviceArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedDevice);
        }
    }

}
