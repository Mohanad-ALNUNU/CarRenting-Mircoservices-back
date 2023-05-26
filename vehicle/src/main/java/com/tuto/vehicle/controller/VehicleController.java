package com.tuto.vehicle.controller;

import com.tuto.vehicle.entity.Vehicle;
import com.tuto.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") String id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/vehicleName/{name}")
    public ResponseEntity<List<Vehicle>> getVehicleByName(@PathVariable("name") String name) {
        Optional<List<Vehicle>> optionalVehicleList = vehicleRepository.findByName(name);
        if (optionalVehicleList.isPresent()) {
            List<Vehicle> vehicleList = optionalVehicleList.get();
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/vehicleModelDate/{modelDate}")
    public ResponseEntity<List<Vehicle>> getVehicleByModelDate(@PathVariable("modelDate") int modelDate) {
        Optional<List<Vehicle>> optionalVehicleList = vehicleRepository.findByModelDate(modelDate);
        if (optionalVehicleList.isPresent() && !optionalVehicleList.get().isEmpty()) {
            List<Vehicle> vehicleList = optionalVehicleList.get();
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/vehiclesNameModelDate/{name}/{modelDate}")
    public ResponseEntity<List<Vehicle>> getVehiclesByNameAndModelDate(@PathVariable("name") String name, @PathVariable("modelDate") int modelDate) {
        Optional<List<Vehicle>> optionalVehicleList = vehicleRepository.findByNameAndModelDate(name, modelDate);
        if (optionalVehicleList.isPresent() &&!optionalVehicleList.get().isEmpty()) {
            List<Vehicle> vehicleList = optionalVehicleList.get();
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") String id, @RequestBody Vehicle updatedVehicle) {
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") String id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            vehicleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
