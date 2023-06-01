package com.tuto.vehicle.exception;

import org.apache.kafka.common.errors.ResourceNotFoundException;

public class VehicleNotFoundException extends ResourceNotFoundException {
    public VehicleNotFoundException(String message){
        super(message);
    }
}
