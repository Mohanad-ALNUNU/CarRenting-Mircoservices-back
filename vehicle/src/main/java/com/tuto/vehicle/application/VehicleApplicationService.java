package com.tuto.vehicle.application;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.exception.VehicleModelException;
import com.tuto.vehicle.exception.VehicleNotFoundException;
import com.tuto.vehicle.service.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleApplicationService {

    private final VehicleServiceImpl vehicleServiceImpl;

    @Autowired
    public VehicleApplicationService(VehicleServiceImpl vehicleServiceImpl) {
        this.vehicleServiceImpl = vehicleServiceImpl;
    }

    public ResponseEntity<Void> createVehicle(Vehicle vehicle) throws VehicleModelException {
        vehicleServiceImpl.createVehicle(vehicle);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleServiceImpl.getAllVehicles());
    }

    public ResponseEntity<Vehicle> getVehicleById(String id) {
        return ResponseEntity.ok(vehicleServiceImpl.getVehicleById(id));
    }

    public ResponseEntity<List<Vehicle>> getVehicleByName(String name) {
        return ResponseEntity.ok(vehicleServiceImpl.getVehicleByName(name));
    }

    public ResponseEntity<List<Vehicle>> getVehicleByModelDate(int modelDate) {
        return ResponseEntity.ok(vehicleServiceImpl.getVehicleByModelDate(modelDate));
    }

    public ResponseEntity<List<Vehicle>> getVehiclesByNameAndModelDate(String name, int modelDate) {
        return ResponseEntity.ok(vehicleServiceImpl.getVehiclesByNameAndModelDate(name, modelDate));
    }

    public ResponseEntity<Vehicle> updateVehicle(String id, Vehicle updatedVehicle) throws VehicleModelException, VehicleNotFoundException {
        return ResponseEntity.ok(vehicleServiceImpl.updateVehicle(id, updatedVehicle));
    }

    public ResponseEntity<Void> deleteVehicle(String id) {
        vehicleServiceImpl.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
