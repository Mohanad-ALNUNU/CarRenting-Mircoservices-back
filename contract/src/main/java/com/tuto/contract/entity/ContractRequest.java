package com.tuto.contract.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContractRequest {
    private String name;
    private String description;
    private String vehicleId;
    private String customerId;

}
