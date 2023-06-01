    package com.tuto.vehicle.service;

    import com.tuto.vehicle.application.VehicleServiceOperations;
    import com.tuto.vehicle.entity.Vehicle;
    import com.tuto.vehicle.exception.VehicleNotFoundException;
    import com.tuto.vehicle.repository.VehicleRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.kafka.core.KafkaTemplate;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class VehicleService implements VehicleServiceOperations {

        private final KafkaTemplate<String, Vehicle> kafkaTemplate;
        private  final VehicleRepository vehicleRepository;

        @Autowired
        public VehicleService(KafkaTemplate<String, Vehicle> kafkaTemplate, VehicleRepository vehicleRepository){
            this.kafkaTemplate = kafkaTemplate;
            this.vehicleRepository = vehicleRepository;
        }

    @Override
    public void createVehicle(Vehicle vehicle){
        kafkaTemplate.send("vehicle", vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle getVehicleById(String id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException("No vehicle found with this ID"));
    }


    @Override
    public List<Vehicle> getVehicleByName(String name) {
        return vehicleRepository.findByName(name);
    }

    @Override
    public List<Vehicle> getVehicleByModelDate(int modelDate) {
        return vehicleRepository.findByModelDate(modelDate);
    }
    @Override
    public List<Vehicle> getVehiclesByNameAndModelDate(String name, int modelDate) {
        return vehicleRepository.findByNameAndModelDate(name, modelDate);
    }

    @Override
    public Vehicle updateVehicle(String id,Vehicle updatedVehicle) {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException("No vehicle found with this ID"));
    }
    @Override
    public void deleteVehicle(String id) {
        vehicleRepository.findById(id);
    }
}
