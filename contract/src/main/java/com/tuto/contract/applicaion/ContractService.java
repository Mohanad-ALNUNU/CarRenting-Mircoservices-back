package com.tuto.contract.applicaion;

import com.tuto.contract.entity.Contract;
import com.tuto.contract.entity.ContractRequest;
import com.tuto.contract.exception.ContractDatesException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContractService {

    void createContract(ContractRequest contractRequest) throws ContractDatesException;

    void updateContract(String id, Contract updatedContract);

    List<Contract> getAllContracts();

    Contract getContractById(String id);

    void deleteContract(String id);
}
