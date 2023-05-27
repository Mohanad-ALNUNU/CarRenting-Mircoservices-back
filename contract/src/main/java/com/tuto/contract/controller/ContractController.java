package com.tuto.contract.controller;

import com.tuto.contract.entity.Contract;
import com.tuto.contract.entity.ContractRequest;
import com.tuto.contract.applicaion.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<String> createContract(@RequestBody ContractRequest contractRequest) {
        return contractService.createContract(contractRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable("id") String id) {
        return contractService.getContractById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable("id") String id, @RequestBody Contract updatedContract) {
        return contractService.updateContract(id, updatedContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable("id") String id) {
        return contractService.deleteContract(id);
    }
}
