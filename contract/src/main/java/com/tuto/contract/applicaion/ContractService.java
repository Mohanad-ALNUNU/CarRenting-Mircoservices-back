package com.tuto.contract.applicaion;

import com.tuto.contract.entity.Contract;
import com.tuto.contract.entity.ContractRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContractService {

    ResponseEntity<String> createContract(ContractRequest contractRequest);

    ResponseEntity<List<Contract>> getAllContracts();

    ResponseEntity<Contract> getContractById(String id);

    ResponseEntity<Contract> updateContract(String id, Contract updatedContract);

    ResponseEntity<Void> deleteContract(String id);
}
