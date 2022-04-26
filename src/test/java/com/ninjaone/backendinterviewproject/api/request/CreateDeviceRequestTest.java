package com.ninjaone.backendinterviewproject.api.request;

import com.ninjaone.backendinterviewproject.database.model.Cost;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CreateDeviceRequestTest {

    @Nested
    class ConvertToNewDevice {
        @Test
        void shouldReturnANewDeviceGivenADeviceRequest() {
            String systemName = "Test System Name";
            DeviceTypeEnum type = DeviceTypeEnum.WINDOWS_SERVER;
            Double value = Double.valueOf("123");
            Device expectedDevice = new Device(systemName, type);
            Cost deviceCost = new Cost(expectedDevice, value, null);
            List<Cost> deviceCosts = Stream.of(deviceCost).collect(Collectors.toList());
            expectedDevice.setCosts(deviceCosts);
            CreateDeviceRequest request = new CreateDeviceRequest();
            DeviceRequest deviceRequest = new DeviceRequest(systemName, type);
            request.setDevice(deviceRequest);
            request.setDeviceCost(value);

            Device actualDevice = request.convertToNewDevice();

            assertThat(actualDevice).usingRecursiveComparison().isEqualTo(expectedDevice);
        }
    }

    @Nested
    class validateForCreate {

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenCreateDeviceRequestIsNull() {
            CreateDeviceRequest nullCreateDeviceRequest = null;
            String expectedMessage = "Request is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                CreateDeviceRequest.validateForCreate(nullCreateDeviceRequest);
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
                CreateDeviceRequest.validateForCreate(createDeviceRequest);
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
                CreateDeviceRequest.validateForCreate(createDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }
    }

    @Nested
    class validateForUpdate {
        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestIsNull() {
            CreateDeviceRequest nullUpdateDeviceRequest = null;
            String expectedMessage = "Request is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                CreateDeviceRequest.validateForUpdate(nullUpdateDeviceRequest);
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
                CreateDeviceRequest.validateForUpdate(updateDeviceRequest);
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
                CreateDeviceRequest.validateForUpdate(updateDeviceRequest);
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }
    }
}
