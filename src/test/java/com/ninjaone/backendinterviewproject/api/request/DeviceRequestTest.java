package com.ninjaone.backendinterviewproject.api.request;

import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DeviceRequestTest {

    @Nested
    class toDevice {
        @Test
        void shouldReturnADeviceObjectGivenADeviceRequest() {
            String systemName = "Test System Name";
            DeviceTypeEnum type = DeviceTypeEnum.MAC;
            DeviceRequest givenDeviceRequest = new DeviceRequest(systemName, type);
            Device expectedDevice = new Device(systemName, type);

            Device actualDevice = givenDeviceRequest.toDevice();

            assertThat(actualDevice).usingRecursiveComparison().isEqualTo(expectedDevice);
        }
    }

    @Nested
    class fromDevice {
        @Test
        void shouldReturnADeviceRequestObjectGivenADevice() {
            String systemName = "Test System Name";
            DeviceTypeEnum type = DeviceTypeEnum.MAC;
            DeviceRequest expectedDeviceRequest = new DeviceRequest(systemName, type);
            Device givenDevice = new Device(systemName, type);

            DeviceRequest actualDeviceRequest = DeviceRequest.fromDevice(givenDevice);

            assertThat(actualDeviceRequest).usingRecursiveComparison().isEqualTo(expectedDeviceRequest);
        }
    }

    @Nested
    class validateForCreate {
        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestHasNullSystemName() {
            Long deviceId = 10L;
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setSystemName(null);
            deviceRequest.setType(DeviceTypeEnum.MAC);
            deviceRequest.setId(deviceId);
            String expectedMessage = "Device system name is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceRequest.validateForCreate();
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }

        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestHasNullType() {
            Long deviceId = 10L;
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setSystemName("Test System Name");
            deviceRequest.setType(null);
            deviceRequest.setId(deviceId);
            String expectedMessage = "Device type is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceRequest.validateForCreate();
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }
    }

    @Nested
    class validateForUpdate {
        @Test
        void shouldThrowAnIllegalArgumentExceptionWhenUpdateDeviceRequestHasNullId() {
            Long deviceId = null;
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setSystemName("System Name");
            deviceRequest.setType(DeviceTypeEnum.MAC);
            deviceRequest.setId(deviceId);
            String expectedMessage = "Device id is null";

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                deviceRequest.validateForUpdate();
            });

            assertEquals(exception.getMessage(), expectedMessage);
        }
    }
}
