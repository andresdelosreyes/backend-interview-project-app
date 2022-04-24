package com.ninjaone.backendinterviewproject.api.request;

import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


class DeviceRequestTest {

    @Test
    void shouldReturnADeviceObjectGivenADeviceRequest() {
        String systemName = "Test System Name";
        DeviceTypeEnum type = DeviceTypeEnum.MAC;
        DeviceRequest givenDeviceRequest = new DeviceRequest(systemName, type);
        Device expectedDevice = new Device(systemName, type);

        Device actualDevice = givenDeviceRequest.toDevice();

        assertThat(actualDevice).usingRecursiveComparison().isEqualTo(expectedDevice);
    }

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