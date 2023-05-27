package com.tuto.contract.service;

import com.tuto.contract.applicaion.ContractService;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, Contract> kafkaTemplate;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, VehicleRepository vehicleRepository,
                               CustomerRepository customerRepository, KafkaTemplate<String, Contract> kafkaTemplate) {
        this.contractRepository = contractRepository;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ResponseEntity<String> createContract(ContractRequest contractRequest) {
        Contract contract = buildContract(contractRequest);

        kafkaTemplate.send("contract", contract);
        return new ResponseEntity<>("Request sent by Kafka", HttpStatus.CREATED);
    }

    private Contract buildContract(ContractRequest contractRequest) {
        Vehicle vehicle = vehicleRepository.findById(contractRequest.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Customer customer = customerRepository.findById(contractRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return Contract.builder()
                .name(contractRequest.getName())
                .description(contractRequest.getDescription())
                .vehicle(vehicle)
                .customer(customer)
                .build();
    }

    @Override
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Contract> getContractById(String id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            Contract contract = optionalContract.get();
            return new ResponseEntity<>(contract, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Contract> updateContract(String id, Contract updatedContract) {
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

    @Override
    public ResponseEntity<Void> deleteContract(String id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            contractRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
