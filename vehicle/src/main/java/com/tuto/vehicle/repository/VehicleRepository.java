package com.tuto.vehicle.repository;

import com.tuto.vehicle.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    Vehicle findByVehicleID(String id);
    List<Vehicle> findByVehicleModelDate(int birthday);
}
