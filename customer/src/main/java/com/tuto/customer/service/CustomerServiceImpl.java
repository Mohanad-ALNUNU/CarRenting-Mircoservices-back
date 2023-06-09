package com.tuto.customer.service;

import com.tuto.customer.application.CustomerService;
import com.tuto.customer.entity.Customer;
import com.tuto.customer.exceptions.CustomerCreationException;
import com.tuto.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    public Optional<Customer> findByCustomerID(String id){
        return customerRepository.findById(id);
    }
    @Override
    public List<Customer> findByCustomerName(String customerName) {
        return customerRepository.findByCustomerName(customerName);
    }

    @Override
    public List<Customer> findByBirthDay(int birthDay) {
        return customerRepository.findByBirthDay(birthDay);
    }

    @Override
    public List<Customer> findByCustomerNameAndBirthDay(String customerName, int birthDay) {
        List<Customer> customersFoundByCustomerName = customerRepository.findByCustomerName(customerName);
        List<Customer> customersFoundByBirthDay = customerRepository.findByBirthDay(birthDay);
        return customersFoundByCustomerName.stream()
                .filter(customersFoundByBirthDay::contains)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public void createCustomer(Customer customer) throws CustomerCreationException {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        boolean isOlderThen18 = currentYear - customer.getBirthDay() > 18;
        if(isOlderThen18){
            kafkaTemplate.send("customer", customer);
        }else {
            throw new CustomerCreationException("Customer is less than 18 year");
        }
    }

    @Override
    public void updateCustomer(String id, Customer customer) throws CustomerCreationException {
        Optional<Customer> existedCustomer = customerRepository.findById(id);
        if (existedCustomer.isPresent()) {
            customer.setId(id);
            createCustomer(customer);
        }
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
