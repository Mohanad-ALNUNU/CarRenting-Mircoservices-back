package com.tuto.vehicle.service;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.exception.VehicleModelException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private KafkaTemplate<String, Vehicle> kafkaTemplate;

    @InjectMocks
    private VehicleServiceImpl vehicleServiceImpl;
    @Test
    void Should_Not_ThrowError_When_ModelDateMoreThan2005() {
        Vehicle vehicle = new Vehicle();
        vehicle.setModelDate(2020);
        when(kafkaTemplate.send(anyString(), any(Vehicle.class))).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> vehicleServiceImpl.createVehicle(vehicle));
    }


    @Test
    @SneakyThrows
    void Should_ThrowError_When_ModelDateLessThan2005() {
        Vehicle vehicle = new Vehicle();
        vehicle.setModelDate(2000);
        Assertions.assertThrows(VehicleModelException.class, () -> vehicleServiceImpl.createVehicle(vehicle));
    }
}