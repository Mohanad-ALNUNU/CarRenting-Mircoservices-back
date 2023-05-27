package com.tuto.customer.application;

import com.tuto.customer.entity.Customer;
import com.tuto.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerApplicationService {

private final CustomerService customerService;
    @Autowired
    public CustomerApplicationService(CustomerService customerService) {
        this.customerService =customerService;
    }

    public List<Customer> searchCustomersByCustomerName(String customerName) {
        return customerService.findByCustomerName(customerName);
    }

    public List<Customer> searchCustomersBybirthDay(int birthDay) {
        return customerService.findByBirthDay(birthDay);
    }

    public Iterable<Customer> searchCustomersByCustomerNameBirthDay(String customerName, int birthDay) {
        return customerService.findByCustomerNameAndBirthDay(customerName, birthDay);
    }

    public Iterable<Customer> getAllCustomersInformation() {
        return customerService.getAllCustomers();
    }

    public ResponseEntity<String> createCustomer(Customer customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>("Request sent to Kafka", HttpStatus.OK);
    }

    public void updateCustomer(String id, Customer customer) {
       customerService.updateCustomer(id, customer);
    }

    public void deleteCustomer(String id) {
        customerService.deleteCustomer(id);
    }
}
