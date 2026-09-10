package com.traffic;

import com.traffic.exception.DuplicateViolationException;
import com.traffic.exception.InvalidVehicleException;
import com.traffic.model.Vehicle;
import com.traffic.model.VehicleType;
import com.traffic.model.Violation;
import com.traffic.model.ViolationType;
import com.traffic.service.VehicleService;
import com.traffic.service.ViolationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ViolationServiceTest {

    private VehicleService vehicleService;
    private ViolationService violationService;

    @BeforeEach
    void setup() {

        vehicleService =
                new VehicleService();

        vehicleService.registerVehicle(
                new Vehicle(
                        "TN58AB1234",
                        "Nishanth",
                        "9876543210",
                        VehicleType.CAR));

        violationService =
                new ViolationService(
                        vehicleService);
    }

    @Test
    void testRecordOverspeeding() {

        Violation violation =
                new Violation(
                        "V001",
                        "TN58AB1234",
                        ViolationType.OVERSPEEDING,
                        "Madurai",
                        LocalDateTime.now(),
                        80,
                        60);

        violationService.recordViolation(
                violation);

        assertEquals(
                1,
                vehicleService
                        .getVehicle("TN58AB1234")
                        .getViolationCount());
    }

    @Test
    void testSpeedExactlyAtPermittedLimit() {

        Violation violation =
                new Violation(
                        "V002",
                        "TN58AB1234",
                        ViolationType.OVERSPEEDING,
                        "Madurai",
                        LocalDateTime.now(),
                        60,
                        60);

        assertThrows(
                InvalidVehicleException.class,
                () -> violationService
                        .recordViolation(violation));
    }

    @Test
    void testNegativeSpeed() {

        Violation violation =
                new Violation(
                        "V003",
                        "TN58AB1234",
                        ViolationType.OVERSPEEDING,
                        "Madurai",
                        LocalDateTime.now(),
                        -10,
                        60);

        assertThrows(
                InvalidVehicleException.class,
                () -> violationService
                        .recordViolation(violation));
    }

    @Test
    void testDuplicateViolation() {

        Violation violation =
                new Violation(
                        "V004",
                        "TN58AB1234",
                        ViolationType.SIGNAL_VIOLATION,
                        "Madurai",
                        LocalDateTime.now(),
                        0,
                        0);

        violationService.recordViolation(
                violation);

        assertThrows(
                DuplicateViolationException.class,
                () -> violationService
                        .recordViolation(violation));
    }

    @Test
    void testFutureTimestamp() {

        Violation violation =
                new Violation(
                        "V005",
                        "TN58AB1234",
                        ViolationType.ILLEGAL_PARKING,
                        "Madurai",
                        LocalDateTime.now()
                                .plusDays(1),
                        0,
                        0);

        assertThrows(
                InvalidVehicleException.class,
                () -> violationService
                        .recordViolation(violation));
    }
}
