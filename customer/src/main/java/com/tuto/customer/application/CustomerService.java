package com.tuto.customer.application;

import com.tuto.customer.entity.Customer;
import com.tuto.customer.exceptions.CustomerCreationException;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByCustomerID(String id);
    List<Customer> findByCustomerName(String customerName);
    List<Customer> findByBirthDay(int birthDay);
    List<Customer> findByCustomerNameAndBirthDay(String customerName, int birthDay);
    List<Customer> getAllCustomers();
    void createCustomer(Customer customer) throws CustomerCreationException;
    void updateCustomer(String id, Customer customer) throws CustomerCreationException;
    void deleteCustomer(String id);
}
