package com.tuto.vehicle.application;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleApplicationService {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleApplicationService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void createVehicle(Vehicle vehicle) {
        vehicleService.sendVehicleEntity(vehicle);
    }

    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    public ResponseEntity<Vehicle> getVehicleById(String id) {
        return vehicleService.getVehicleById(id);
    }

    public ResponseEntity<List<Vehicle>> getVehicleByName(String name) {
        return vehicleService.getVehicleByName(name);
    }

    public ResponseEntity<List<Vehicle>> getVehicleByModelDate(int modelDate) {
        return vehicleService.getVehicleByModelDate(modelDate);
    }

    public ResponseEntity<List<Vehicle>> getVehiclesByNameAndModelDate(String name, int modelDate) {
        return vehicleService.getVehiclesByNameAndModelDate(name, modelDate);
    }

    public ResponseEntity<Vehicle> updateVehicle(String id, Vehicle updatedVehicle) {
        return vehicleService.updateVehicle(id, updatedVehicle);
    }

    public ResponseEntity<Void> deleteVehicle(String id) {
        return vehicleService.deleteVehicle(id);
    }
}
