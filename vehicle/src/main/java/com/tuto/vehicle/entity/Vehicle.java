package com.tuto.vehicle.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "vehicle")
public class Vehicle {

    @Id
    private String id;

    private String name;

    @Field("model_date")
    private int modelDate;

    private VehicleType vehicleType;

    public enum VehicleType {
        JEEP,
        SPORT,
        ECONOMIC,
        AGRICULTURE,
        LOGISTIC
    }
}
