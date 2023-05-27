package com.tuto.customer.repository;

import com.tuto.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    List<Customer> findByCustomerName(String name);
    List<Customer> findByBirthDay(int birthday);

}
