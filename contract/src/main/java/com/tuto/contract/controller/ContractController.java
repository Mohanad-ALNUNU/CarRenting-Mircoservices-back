package com.tuto.contract.controller;

import com.tuto.contract.entity.Contract;
import com.tuto.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractController(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        Contract savedContract = contractRepository.save(contract);
        return new ResponseEntity<>(savedContract, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable("id") String id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            Contract contract = optionalContract.get();
            return new ResponseEntity<>(contract, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable("id") String id, @RequestBody Contract updatedContract) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            Contract existingContract = optionalContract.get();
            existingContract.setName(updatedContract.getName());
            existingContract.setDescription(updatedContract.getDescription());
            existingContract.setClientName(updatedContract.getClientName());

            Contract savedContract = contractRepository.save(existingContract);
            return new ResponseEntity<>(savedContract, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable("id") String id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            contractRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
