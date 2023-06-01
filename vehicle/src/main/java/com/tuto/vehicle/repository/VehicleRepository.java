package com.tuto.vehicle.repository;

import com.tuto.vehicle.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    Optional<Vehicle> findById(String id);
    List<Vehicle> findByModelDate(int modelDate);
    List<Vehicle> findByName(String name);

    List<Vehicle> findByNameAndModelDate(String name, int modelDate);
}
