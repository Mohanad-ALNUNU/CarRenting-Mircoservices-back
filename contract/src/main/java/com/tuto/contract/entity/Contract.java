package com.tuto.contract.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "contracts")
public class Contract {

    @Id
    private String id;
    private String name;
    private String description;
    private String clientName;

    // Constructors, getters, and setters
}
