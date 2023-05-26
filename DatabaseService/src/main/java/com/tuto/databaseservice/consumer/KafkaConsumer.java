package com.tuto.databaseservice.consumer;


import com.tuto.contract.entity.Contract;
import com.tuto.customer.entity.Customer;
import com.tuto.vehicle.entity.Vehicle;
import com.tuto.databaseservice.repository.ContractRepository;
import com.tuto.databaseservice.repository.CustomerRepository;
import com.tuto.databaseservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ContractRepository contractRepository;

    @Autowired
    KafkaConsumer(CustomerRepository customerRepository, ContractRepository contractRepository, VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.contractRepository = contractRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @KafkaListener(topics = "contract", groupId = "inserting", containerFactory = "contractKafkaListenerContainerFactory")
    public void createContract(Contract contract) {
        contractRepository.save(contract);
    }

    @KafkaListener(topics = "customer", groupId = "inserting", containerFactory = "customerKafkaListenerContainerFactory")
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @KafkaListener(topics = "vehicle", groupId = "inserting", containerFactory = "vehicleKafkaListenerContainerFactory")
    public void createVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }
}
