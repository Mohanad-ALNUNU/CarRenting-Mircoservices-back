package com.tuto.customer.service;

import com.tuto.customer.application.CustomerService;
import com.tuto.customer.entity.Customer;
import com.tuto.customer.repository.CustomerRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final KafkaTemplate<String, Customer> kafkaTemplate;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(KafkaTemplate<String, Customer> kafkaTemplate, CustomerRepository customerRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findByCustomername(String customername) {
        return customerRepository.findByCustomername(customername);
    }

    @Override
    public List<Customer> findByBirthDay(int birthDay) {
        return customerRepository.findByBirthDay(birthDay);
    }

    @Override
    public List<Customer> findByCustomernameAndBirthDay(String customername, int birthDay) {
        List<Customer> customersFoundByCustomername = customerRepository.findByCustomername(customername);
        List<Customer> customersFoundByBirthDay = customerRepository.findByBirthDay(birthDay);
        return customersFoundByCustomername.stream()
                .filter(customersFoundByBirthDay::contains)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public void createCustomer(Customer customer) {
        kafkaTemplate.send("customer", customer);
    }

    @Override
    public void updateCustomer(String id, Customer customer) {
        Optional<Customer> existedCustomer = customerRepository.findById(id);
        existedCustomer.ifPresent(existing -> {
            customer.setId(id);
            customerRepository.save(customer);
        });
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
