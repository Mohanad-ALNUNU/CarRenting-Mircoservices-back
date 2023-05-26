    package com.tuto.vehicle.service;

    import com.tuto.vehicle.entity.Vehicle;
    import com.tuto.vehicle.repository.VehicleRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.kafka.core.KafkaTemplate;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class VehicleService {

        private final KafkaTemplate<String, Vehicle> kafkaTemplate;
        private  final VehicleRepository vehicleRepository;

        @Autowired
        public VehicleService(KafkaTemplate<String, Vehicle> kafkaTemplate, VehicleRepository vehicleRepository){
            this.kafkaTemplate = kafkaTemplate;
            this.vehicleRepository = vehicleRepository;
        }

        public void sendVehicleEntity(Vehicle vehicle){
            kafkaTemplate.send("vehicle", vehicle);
        }

    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        if(allVehicles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allVehicles, HttpStatus.OK);
    }

    public ResponseEntity<Vehicle> getVehicleById(String id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<List<Vehicle>> getVehicleByName(String name) {
        Optional<List<Vehicle>> optionalVehicleList = vehicleRepository.findByName(name);
        if (optionalVehicleList.isPresent()) {
            List<Vehicle> vehicleList = optionalVehicleList.get();
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Vehicle>> getVehicleByModelDate(int modelDate) {
        Optional<List<Vehicle>> optionalVehicleList = vehicleRepository.findByModelDate(modelDate);
        if (optionalVehicleList.isPresent() && !optionalVehicleList.get().isEmpty()) {
            List<Vehicle> vehicleList = optionalVehicleList.get();
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<List<Vehicle>> getVehiclesByNameAndModelDate(String name, int modelDate) {
        Optional<List<Vehicle>> optionalVehicleList = vehicleRepository.findByNameAndModelDate(name, modelDate);
        if (optionalVehicleList.isPresent() &&!optionalVehicleList.get().isEmpty()) {
            List<Vehicle> vehicleList = optionalVehicleList.get();
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Vehicle> updateVehicle(String id,Vehicle updatedVehicle) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle existingVehicle = optionalVehicle.get();
            existingVehicle.setName(updatedVehicle.getName());
            existingVehicle.setModelDate(updatedVehicle.getModelDate());
            existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
            Vehicle savedVehicle = vehicleRepository.save(existingVehicle);
            return new ResponseEntity<>(savedVehicle, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<Void> deleteVehicle(String id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            vehicleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
