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

    private final KafkaTemplate<String, Customer> kafkaTemplate;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerApplicationService(KafkaTemplate<String, Customer> kafkaTemplate, CustomerRepository customerRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerRepository = customerRepository;
    }

    public List<Customer> searchCustomersByCustomername(String customername) {
        return customerRepository.findByCustomername(customername);
    }

    public List<Customer> searchCustomersBybirthDay(int birthDay) {
        return customerRepository.findByBirthDay(birthDay);
    }

    public List<Customer> searchCustomersByCustomernameBirthDay(String customername, int birthDay) {
        List<Customer> customersFoundByCustomername = customerRepository.findByCustomername(customername);
        List<Customer> customersFoundByBirthDay = customerRepository.findByBirthDay(birthDay);
        return customersFoundByCustomername.stream()
                .filter(customersFoundByBirthDay::contains)
                .collect(Collectors.toList());
    }

    public Iterable<Customer> getAllCustomersInformation() {
        return customerRepository.findAll();
    }

    public ResponseEntity<String> createCustomer(Customer customer) {
        kafkaTemplate.send("customer", customer);
        return new ResponseEntity<>("Request sent to Kafka", HttpStatus.OK);
    }

    public ResponseEntity<Customer> updateCustomer(String id, Customer customer) {
        Optional<Customer> existedCustomer = customerRepository.findById(id);
        if (existedCustomer.isPresent()) {
            customer.setId(id);
            Customer savedCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(savedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteCustomer(String id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
