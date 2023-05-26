package com.tuto.vehicle.controller;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.repository.VehicleRepository;
import com.tuto.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {

        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<String> createVehicle(@RequestBody Vehicle vehicle) {
            vehicleService.sendVehicleEntity(vehicle);
        return new  ResponseEntity<>("Vehicle sent", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") String id) {
        return vehicleService.getVehicleById(id);
    }

    @GetMapping("/search/vehicleName/{name}")
    public ResponseEntity<List<Vehicle>> getVehicleByName(@PathVariable("name") String name) {
        return vehicleService.getVehicleByName(name);
    }

    @GetMapping("/search/vehicleModelDate/{modelDate}")
    public ResponseEntity<List<Vehicle>> getVehicleByModelDate(@PathVariable("modelDate") int modelDate) {
        return vehicleService.getVehicleByModelDate(modelDate);
    }
    @GetMapping("/search/vehiclesNameModelDate/{name}/{modelDate}")
    public ResponseEntity<List<Vehicle>> getVehiclesByNameAndModelDate(@PathVariable("name") String name, @PathVariable("modelDate") int modelDate) {
        return vehicleService.getVehiclesByNameAndModelDate(name, modelDate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") String id, @RequestBody Vehicle updatedVehicle) {
        return vehicleService.updateVehicle(id, updatedVehicle);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") String id) {
        return vehicleService.deleteVehicle(id);
    }
}
