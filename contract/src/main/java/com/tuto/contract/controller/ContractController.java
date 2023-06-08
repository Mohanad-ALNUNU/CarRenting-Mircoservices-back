package com.tuto.contract.controller;

import com.tuto.contract.entity.Contract;
import com.tuto.contract.entity.ContractRequest;
import com.tuto.contract.applicaion.ContractService;
import com.tuto.contract.exception.ContractDatesException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/contracts")
@Api(tags = "Contract APIs")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<String> createContract(@RequestBody ContractRequest contractRequest) throws ContractDatesException {
             contractService.createContract(contractRequest);

        return ResponseEntity.ok("Contract sent");
    }

    @GetMapping("/all")
    public List<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/search/id/{id}")
    public Contract getContractById(@PathVariable("id") String id) {
        return contractService.getContractById(id);
    }

    @PutMapping("/{id}")
    public void updateContract(@PathVariable("id") String id, @RequestBody Contract updatedContract) {
        contractService.updateContract(id, updatedContract);
    }


    @DeleteMapping("/{id}")
    public void deleteContract(@PathVariable("id") String id) {
        contractService.deleteContract(id);
    }
}
