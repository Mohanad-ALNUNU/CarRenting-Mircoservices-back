    package com.tuto.vehicle.service;

    import com.tuto.vehicle.application.VehicleServiceOperations;
    import com.tuto.vehicle.entity.Vehicle;
    import com.tuto.vehicle.exception.VehicleModelException;
    import com.tuto.vehicle.exception.VehicleNotFoundException;
    import com.tuto.vehicle.repository.VehicleRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.kafka.core.KafkaTemplate;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class VehicleServiceImpl implements VehicleServiceOperations {

        private final KafkaTemplate<String, Vehicle> kafkaTemplate;
        private  final VehicleRepository vehicleRepository;

        @Autowired
        public VehicleServiceImpl(KafkaTemplate<String, Vehicle> kafkaTemplate, VehicleRepository vehicleRepository){
            this.kafkaTemplate = kafkaTemplate;
            this.vehicleRepository = vehicleRepository;
        }

    @Override
    public void createVehicle(Vehicle vehicle) throws VehicleModelException{
        if(vehicle.getModelDate() > 2005){
            kafkaTemplate.send("vehicle", vehicle);
        }else{
            throw new VehicleModelException("couldn't update Vehicle have model less than 2005");
        }
    }

    @Override
    public Vehicle updateVehicle(String id,Vehicle updatedVehicle) throws VehicleModelException, VehicleNotFoundException {
            vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException("No vehicle found with this ID"));
            updatedVehicle.setId(id);
            createVehicle(updatedVehicle);
            return updatedVehicle;
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
    public void deleteVehicle(String id) {
        vehicleRepository.findById(id);
    }
}
