package com.tuto.contract.entity;

import com.tuto.customer.entity.Customer;
import com.tuto.vehicle.entity.Vehicle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "contracts")
public class Contract {

    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private Vehicle vehicle;
    @DBRef
    private Customer customer;
}
