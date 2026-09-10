package com.traffic.service;

import com.traffic.exception.DuplicateViolationException;
import com.traffic.exception.InvalidVehicleException;
import com.traffic.model.Vehicle;
import com.traffic.model.Violation;
import com.traffic.model.ViolationType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ViolationService {

    private final VehicleService vehicleService;

    private final Set<String> processedViolationIds =
            new HashSet<>();

    public ViolationService(
            VehicleService vehicleService) {

        this.vehicleService = vehicleService;
    }

    public void recordViolation(Violation violation) {

        if (violation == null) {

            throw new InvalidVehicleException(
                    "Violation information cannot be null");
        }

        if (violation.getViolationId() == null ||
                violation.getViolationId().trim().isEmpty()) {

            throw new InvalidVehicleException(
                    "Violation ID cannot be empty");
        }

        if (violation.getVehicleNumber() == null ||
                violation.getVehicleNumber().trim().isEmpty()) {

            throw new InvalidVehicleException(
                    "Vehicle number cannot be empty");
        }

        if (violation.getViolationType() == null) {

            throw new InvalidVehicleException(
                    "Violation type cannot be null");
        }

        if (violation.getLocation() == null ||
                violation.getLocation().trim().isEmpty()) {

            throw new InvalidVehicleException(
                    "Violation location cannot be empty");
        }

        if (violation.getTimestamp() == null) {

            throw new InvalidVehicleException(
                    "Violation timestamp cannot be null");
        }

        if (violation.getTimestamp()
                .isAfter(LocalDateTime.now())) {

            throw new InvalidVehicleException(
                    "Violation timestamp cannot be in the future");
        }

        if (violation.getViolationType()
                == ViolationType.OVERSPEEDING) {

            if (violation.getSpeed() < 0 ||
                    violation.getPermittedSpeed() < 0) {

                throw new InvalidVehicleException(
                        "Speed values cannot be negative");
            }

            if (violation.getSpeed()
                    <= violation.getPermittedSpeed()) {

                throw new InvalidVehicleException(
                        "Vehicle speed must exceed permitted speed for overspeeding");
            }
        }

        if (processedViolationIds.contains(
                violation.getViolationId())) {

            throw new DuplicateViolationException(
                    "Duplicate violation event: " +
                            violation.getViolationId());
        }

        Vehicle vehicle =
                vehicleService.getVehicle(
                        violation.getVehicleNumber());

        vehicle.addViolation(violation);

        processedViolationIds.add(
                violation.getViolationId());
    }

    public boolean isDuplicate(String violationId) {

        return processedViolationIds.contains(
                violationId);
    }
}
