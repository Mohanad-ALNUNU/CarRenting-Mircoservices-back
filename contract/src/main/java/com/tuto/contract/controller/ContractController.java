package com.tuto.contract.controller;

import com.tuto.contract.entity.Contract;
import com.tuto.contract.entity.ContractRequest;
import com.tuto.contract.exception.ResourceNotFoundException;
import com.tuto.contract.repository.ContractRepository;
import com.tuto.customer.repository.CustomerRepository;
import com.tuto.vehicle.repository.VehicleRepository;
import com.tuto.vehicle.entity.Vehicle;
import com.tuto.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractRepository contractRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    public ContractController(ContractRepository contractRepository, VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.contractRepository = contractRepository;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody ContractRequest contractRequest) {
        // Retrieve the Vehicle and Customer entities based on the provided IDs
        Vehicle vehicle = vehicleRepository.findById(contractRequest.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Customer customer = customerRepository.findById(contractRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Create a new Contract object and set the Vehicle and Customer references
        Contract contract = new Contract();
        contract.setName(contractRequest.getName());
        contract.setDescription(contractRequest.getDescription());
        contract.setVehicle(vehicle);
        contract.setCustomer(customer);

        // Save the Contract entity
        Contract savedContract = contractRepository.save(contract);
        return new ResponseEntity<>(savedContract, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("/search/id/{id}")
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
            existingContract.setCustomer(updatedContract.getCustomer());
            existingContract.setVehicle(updatedContract.getVehicle());

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
