package com.tuto.customer.controller;

import com.tuto.customer.entity.Customer;
import com.tuto.customer.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customers")
public record CustomerController(CustomerRepository customerRepository) {



    @GetMapping("/")
    private String rootPage() {

        return "Welcome";
    }

    @GetMapping("/customers/search/customername/{customername}")
    private List<Customer> searchCustomersByCustomername(@PathVariable String customername) {
        return customerRepository.findByCustomername(customername);
    }

    @GetMapping("/customers/search/birthDay/{birthDay}")
    private List<Customer> searchCustomersBybirthDay(@PathVariable int birthDay) {
        return customerRepository.findByBirthDay(birthDay);
    }

    @GetMapping("/customers/search/customernameBirthDay/{customername}/{birthDay}")
    private List<Customer> searchCustomersByCustomernameBirthDay(@PathVariable String customername, @PathVariable int birthDay) {
        List<Customer> customersFoundByCustomername = customerRepository.findByCustomername(customername);
        List<Customer> customersFoundByBirthDay = customerRepository.findByBirthDay(birthDay);
        List<Customer> intersection = customersFoundByCustomername.stream()
                .filter(customersFoundByBirthDay::contains)
                .collect(Collectors.toList());
        return intersection;
    }

    @GetMapping("/customers/all")
    private Iterable<Customer> getAllCustomersInformation() {
        return customerRepository.findAll();

    }

    @PostMapping
    private Customer addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping("customers/update/{id}")
    private ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        Optional<Customer> existedCustomer = customerRepository.findById(id);
        if (existedCustomer.isPresent()) {
            customer.setId(id);
            Customer savedCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(savedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("customers/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
