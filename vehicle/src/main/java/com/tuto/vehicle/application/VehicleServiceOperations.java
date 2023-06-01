package com.tuto.vehicle.application;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.exception.VehicleModelException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleServiceOperations {
    void createVehicle(Vehicle vehicle) throws VehicleModelException;

    Vehicle updateVehicle(String id, Vehicle updatedVehicle) throws VehicleModelException;


    List<Vehicle> getAllVehicles();

    Vehicle getVehicleById(String id);

    List<Vehicle> getVehicleByName(String name);

    List<Vehicle> getVehicleByModelDate(int modelDate);

    List<Vehicle> getVehiclesByNameAndModelDate(String name, int modelDate);

    void deleteVehicle(String id);
}
