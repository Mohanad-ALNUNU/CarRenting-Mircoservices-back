package com.tuto.contract.entity;

import com.tuto.customer.entity.Customer;
import com.tuto.vehicle.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "contracts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    private String id;
    private String name;
    private String description;
    private String imageURL;
    private long contractCreationTimeStamp;
    private long contractBeginTimeStamp;
    private long contractEndTimeStamp;
    private boolean confirmed;
    @DBRef
    private Vehicle vehicle;
    @DBRef
    private Customer customer;
}
