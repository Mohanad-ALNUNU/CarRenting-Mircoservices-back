package com.tuto.vehicle.application;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<Void> createVehicle(Vehicle vehicle) {
        vehicleService.createVehicle(vehicle);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    public ResponseEntity<Vehicle> getVehicleById(String id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    public ResponseEntity<List<Vehicle>> getVehicleByName(String name) {
        return ResponseEntity.ok(vehicleService.getVehicleByName(name));
    }

    public ResponseEntity<List<Vehicle>> getVehicleByModelDate(int modelDate) {
        return ResponseEntity.ok(vehicleService.getVehicleByModelDate(modelDate));
    }

    public ResponseEntity<List<Vehicle>> getVehiclesByNameAndModelDate(String name, int modelDate) {
        return ResponseEntity.ok(vehicleService.getVehiclesByNameAndModelDate(name, modelDate));
    }

    public ResponseEntity<Vehicle> updateVehicle(String id, Vehicle updatedVehicle) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, updatedVehicle));
    }

    public ResponseEntity<Void> deleteVehicle(String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
