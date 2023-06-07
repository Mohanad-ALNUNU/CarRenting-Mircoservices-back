package com.tuto.contract.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest {
    private String name;
    private String description;
    private String imageURL;
    private long contractCreationTimeStamp;
    private long contractBeginTimeStamp;
    private long contractEndTimeStamp;
    private boolean confirmed;
    private String vehicleId;
    private String customerId;

}
