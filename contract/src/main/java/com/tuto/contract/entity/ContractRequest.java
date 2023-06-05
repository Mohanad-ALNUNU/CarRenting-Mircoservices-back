package com.tuto.contract.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Period;
import java.util.Date;

@Data
@NoArgsConstructor
public class ContractRequest {
    private String name;
    private String description;
    private long contractCreationDate;
    private long contractBeginDate;
    private long contractEndDate;
    private boolean confirmed;
    private String vehicleId;
    private String customerId;

}
