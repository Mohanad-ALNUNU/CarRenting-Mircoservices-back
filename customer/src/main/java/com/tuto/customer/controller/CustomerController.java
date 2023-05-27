package com.tuto.customer.controller;

import com.tuto.customer.entity.Customer;
import com.tuto.customer.repository.CustomerRepository;
import com.tuto.customer.application.CustomerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final KafkaTemplate<String, Customer> kafkaTemplate;
    private final CustomerApplicationService customerApplicationService;

    @Autowired
    public CustomerController(KafkaTemplate<String, Customer> kafkaTemplate, CustomerApplicationService customerApplicationService) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerApplicationService = customerApplicationService;
    }

    @GetMapping("/")
    public String rootPage() {
        return "Welcome";
    }

    @GetMapping("/search/customername/{customername}")
    public List<Customer> searchCustomersByCustomername(@PathVariable String customername) {
        return customerApplicationService.searchCustomersByCustomerName(customername);
    }

    @GetMapping("/search/birthDay/{birthDay}")
    public List<Customer> searchCustomersBybirthDay(@PathVariable int birthDay) {
        return customerApplicationService.searchCustomersBybirthDay(birthDay);
    }

    @GetMapping("/search/customernameBirthDay/{customername}/{birthDay}")
    public Iterable<Customer> searchCustomersByCustomernameBirthDay(@PathVariable String customername, @PathVariable int birthDay) {
        return customerApplicationService.searchCustomersByCustomerNameBirthDay(customername, birthDay);
    }

    @GetMapping("/all")
    public Iterable<Customer> getAllCustomersInformation() {
        return customerApplicationService.getAllCustomersInformation();
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        return customerApplicationService.createCustomer(customer);
    }

    @PutMapping("update/{id}")
    public void updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customerApplicationService.updateCustomer(id, customer);
    }

    @DeleteMapping("delete/{id}")
    public void deleteCustomer(@PathVariable("id") String id) {
        customerApplicationService.deleteCustomer(id);
    }
}
