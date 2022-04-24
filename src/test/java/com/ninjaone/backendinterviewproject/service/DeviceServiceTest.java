package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.model.ActiveEnum;
import com.ninjaone.backendinterviewproject.database.model.Device;
import com.ninjaone.backendinterviewproject.database.repository.DeviceRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
}