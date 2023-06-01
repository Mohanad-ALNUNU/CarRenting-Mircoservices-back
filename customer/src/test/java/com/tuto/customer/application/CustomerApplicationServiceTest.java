package com.tuto.customer.application;

import com.tuto.customer.entity.Customer;
import com.tuto.customer.exceptions.CustomerCreationException;
import com.tuto.customer.service.CustomerServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerApplicationServiceTest {

    @Mock
    private KafkaTemplate<String, Customer> kafkaTemplate;
    @InjectMocks
    private CustomerServiceImpl customerService1;
        @Test
        @SneakyThrows
        void Should_Not_ThrowError_When_AgeLessThan18() {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            Customer customer1 = new Customer();
            customer1.setBirthDay(currentYear - 19);
            when(kafkaTemplate.send(anyString(), any(Customer.class))).thenReturn(null);
            Assertions.assertDoesNotThrow(()->customerService1.createCustomer(customer1));
        }
        @Test
        @SneakyThrows
        void Should_ThrowError_When_AgeLessThan18() {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            Customer customer2 = new Customer();
            customer2.setBirthDay(currentYear - 17);
            Assertions.assertThrows(CustomerCreationException.class,()->customerService1.createCustomer(customer2));
        }


}