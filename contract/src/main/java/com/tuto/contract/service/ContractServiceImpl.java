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
    public void createContract(ContractRequest contractRequest) {
        Contract contract = buildContract(contractRequest);
        kafkaTemplate.send("contract", contract);
    }

    private Contract buildContract(ContractRequest contractRequest) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(contractRequest.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Customer customer = customerRepository.findById(contractRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return Contract.builder()
                .name(contractRequest.getName())
                .description(contractRequest.getDescription())
                .vehicle(vehicle)
                .customer(customer)
                .contractCreationDate(contractRequest.getContractCreationDate())
                .contractBeginDate(contractRequest.getContractBeginDate())
                .contractEndDate(contractRequest.getContractEndDate())
                .confirmed(contractRequest.isConfirmed())
                .build();
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract getContractById(String id) throws ResourceNotFoundException {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            return optionalContract.get();
        }else {
            throw new ResourceNotFoundException("No contract found with this ID");
        }

    }

    @Override
    public void updateContract(String id, Contract updatedContract) throws ResourceNotFoundException{
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            Contract existingContract = optionalContract.get();
            updatedContract.setId(existingContract.getId());
            //TODO :: use kafka instead (By making id null and send it)
            contractRepository.save(updatedContract);
        }else {
            throw new ResourceNotFoundException("No contract found with this ID");
        }
    }

    @Override
    public void deleteContract(String id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            contractRepository.deleteById(id);
        }
    }
}
