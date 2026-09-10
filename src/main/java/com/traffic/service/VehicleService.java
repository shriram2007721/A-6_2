package com.traffic.service;

import com.traffic.exception.InvalidVehicleException;
import com.traffic.model.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class VehicleService {

    private final Map<String, Vehicle> vehicles =
            new HashMap<>();

    public void registerVehicle(Vehicle vehicle) {

        if (vehicle == null) {
            throw new InvalidVehicleException(
                    "Vehicle information cannot be null");
        }

        if (vehicle.getVehicleNumber() == null ||
                vehicle.getVehicleNumber().trim().isEmpty()) {

            throw new InvalidVehicleException(
                    "Vehicle number cannot be empty");
        }

        if (vehicle.getOwnerName() == null ||
                vehicle.getOwnerName().trim().isEmpty()) {

            throw new InvalidVehicleException(
                    "Owner name cannot be empty");
        }

        if (vehicle.getOwnerPhone() == null ||
                !vehicle.getOwnerPhone().matches("\\d{10}")) {

            throw new InvalidVehicleException(
                    "Owner phone number must contain exactly 10 digits");
        }

        if (vehicle.getVehicleType() == null) {

            throw new InvalidVehicleException(
                    "Vehicle type cannot be null");
        }

        String vehicleNumber =
                vehicle.getVehicleNumber()
                        .trim()
                        .toUpperCase();

        if (vehicles.containsKey(vehicleNumber)) {

            throw new InvalidVehicleException(
                    "Vehicle already registered: " +
                            vehicleNumber);
        }

        vehicles.put(vehicleNumber, vehicle);
    }

    public Vehicle getVehicle(String vehicleNumber) {

        if (vehicleNumber == null ||
                vehicleNumber.trim().isEmpty()) {

            throw new InvalidVehicleException(
                    "Vehicle number cannot be empty");
        }

        String number =
                vehicleNumber.trim().toUpperCase();

        Vehicle vehicle = vehicles.get(number);

        if (vehicle == null) {

            throw new InvalidVehicleException(
                    "Vehicle not found: " + number);
        }

        return vehicle;
    }

    public int getVehicleCount() {
        return vehicles.size();
    }
}
