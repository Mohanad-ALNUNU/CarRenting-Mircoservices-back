package com.tuto.customer.application;

import com.tuto.customer.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findByCustomername(String customername);
    List<Customer> findByBirthDay(int birthDay);
    List<Customer> findByCustomernameAndBirthDay(String customername, int birthDay);
    List<Customer> getAllCustomers();
    void createCustomer(Customer customer);
    void updateCustomer(String id, Customer customer);
    void deleteCustomer(String id);
}
