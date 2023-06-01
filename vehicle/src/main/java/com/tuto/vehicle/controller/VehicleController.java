package com.tuto.vehicle.controller;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.application.VehicleApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Api(tags = "Vehicle APIs")
public class VehicleController {

    private final VehicleApplicationService vehicleApplicationService;

    @Autowired
    public VehicleController(VehicleApplicationService vehicleApplicationService) {

        this.vehicleApplicationService = vehicleApplicationService;
    }

    @PostMapping
    public ResponseEntity<String> createVehicle(@RequestBody Vehicle vehicle) {
            vehicleApplicationService.createVehicle(vehicle);
        return new  ResponseEntity<>("Vehicle sent", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return vehicleApplicationService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") String id) {
        return vehicleApplicationService.getVehicleById(id);
    }

    @GetMapping("/search/vehicleName/{name}")
    public ResponseEntity<List<Vehicle>> getVehicleByName(@PathVariable("name") String name) {
        return vehicleApplicationService.getVehicleByName(name);
    }

    @GetMapping("/search/vehicleModelDate/{modelDate}")
    public ResponseEntity<List<Vehicle>> getVehicleByModelDate(@PathVariable("modelDate") int modelDate) {
        return vehicleApplicationService.getVehicleByModelDate(modelDate);
    }
    @GetMapping("/search/vehiclesNameModelDate/{name}/{modelDate}")
    public ResponseEntity<List<Vehicle>> getVehiclesByNameAndModelDate(@PathVariable("name") String name, @PathVariable("modelDate") int modelDate) {
        return vehicleApplicationService.getVehiclesByNameAndModelDate(name, modelDate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") String id, @RequestBody Vehicle updatedVehicle) {
        return vehicleApplicationService.updateVehicle(id, updatedVehicle);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") String id) {
        return vehicleApplicationService.deleteVehicle(id);
    }
}
