package com.ninjaone.backendinterviewproject.api.request;

import com.ninjaone.backendinterviewproject.database.model.Cost;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.model.DeviceTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CreateDeviceRequestTest {

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
