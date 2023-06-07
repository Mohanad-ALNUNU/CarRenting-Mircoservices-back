package com.tuto.contract.service;

import com.tuto.contract.entity.Contract;
import com.tuto.contract.entity.ContractRequest;
import com.tuto.contract.exception.ContractDatesException;
import com.tuto.contract.exception.ResourceNotFoundException;
import com.tuto.contract.repository.ContractRepository;
import com.tuto.customer.entity.Customer;
import com.tuto.customer.repository.CustomerRepository;
import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.repository.VehicleRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContractServiceImplTest {


    @Mock
    KafkaTemplate<String, Contract> kafkaTemplate;

    @InjectMocks
    ContractServiceImpl contractServiceImpl;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;


    @SneakyThrows
    @Test
    void Should_ThrowError_When_BeginDateMoreThanEndDate() {
        ContractRequest contractRequest =  ContractRequest.builder()
                .name("name")
                .description("description")
                .vehicleId("vehicleId")
                .customerId("customerId")
                .contractCreationTimeStamp(1686165105)
                .contractBeginTimeStamp(1686856305) //2023-06-15
                .contractEndTimeStamp(1686165105) //2023-06-07
                .confirmed(true)
                .build();
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(new Vehicle()));
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(new Customer()));

        Assertions.assertThrows(ContractDatesException.class, () -> contractServiceImpl.createContract(contractRequest));
    }

    @Test
    void ShouldNot_ThrowError_When_EndDateMoreThanBeginDate() {
        ContractRequest contractRequest =  ContractRequest.builder()
                .name("name")
                .description("description")
                .vehicleId("vehicleId")
                .customerId("customerId")
                .contractCreationTimeStamp(1686165105)
                .contractBeginTimeStamp(1686165105) //2023-06-07
                .contractEndTimeStamp(1686856305) //2023-06-15
                .confirmed(true)
                .build();
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(new Vehicle()));
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(new Customer()));
        when(kafkaTemplate.send(anyString(), any(Contract.class))).thenReturn(null);

        Assertions.assertDoesNotThrow(() -> contractServiceImpl.createContract(contractRequest));
    }

    @Test
    void Should_ThrowError_When_VehicleNotFound() {
        ContractRequest contractRequest =  ContractRequest.builder()
                .name("name")
                .description("description")
                .vehicleId("vehicleId")
                .customerId("customerId")
                .contractCreationTimeStamp(1686165105)
                .contractBeginTimeStamp(1686165105) //2023-06-07
                .contractEndTimeStamp(1686856305) //2023-06-15
                .confirmed(true)
                .build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> contractServiceImpl.createContract(contractRequest));
    }

    @Test
    void Should_ThrowError_When_CustomerNotFound() {
        ContractRequest contractRequest =  ContractRequest.builder()
                .name("name")
                .description("description")
                .vehicleId("vehicleId")
                .customerId("customerId")
                .contractCreationTimeStamp(1686165105)
                .contractBeginTimeStamp(1686165105) //2023-06-07
                .contractEndTimeStamp(1686856305) //2023-06-15
                .confirmed(true)
                .build();

        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(new Vehicle()));

        Assertions.assertThrows(ResourceNotFoundException.class, () -> contractServiceImpl.createContract(contractRequest));
    }
}