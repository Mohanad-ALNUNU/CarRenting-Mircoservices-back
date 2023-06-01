package com.tuto.customer.controller;

import com.tuto.customer.entity.Customer;
import com.tuto.customer.exceptions.CustomerCreationException;
import com.tuto.customer.repository.CustomerRepository;
import com.tuto.customer.application.CustomerApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/customers")
@Api(tags = "Customer APIs")
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
    public List<Customer> searchCustomersByBirthDay(@PathVariable int birthDay) {
        return customerApplicationService.searchCustomersByBirthDay(birthDay);
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
        try {
            customerApplicationService.createCustomer(customer);
        } catch (CustomerCreationException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok("Request sent to Kafka");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        try {
            customerApplicationService.updateCustomer(id, customer);
        } catch (CustomerCreationException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok("Request sent to Kafka");
    }

    @DeleteMapping("delete/{id}")
    public void deleteCustomer(@PathVariable("id") String id) {
        customerApplicationService.deleteCustomer(id);
    }
}
