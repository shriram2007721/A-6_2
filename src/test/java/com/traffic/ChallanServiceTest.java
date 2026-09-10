package com.traffic;

import com.traffic.model.Challan;
import com.traffic.model.Vehicle;
import com.traffic.model.VehicleType;
import com.traffic.model.Violation;
import com.traffic.model.ViolationType;
import com.traffic.service.ChallanService;
import com.traffic.service.VehicleService;
import com.traffic.service.ViolationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChallanServiceTest {

    private VehicleService vehicleService;
    private ViolationService violationService;
    private ChallanService challanService;

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

        challanService =
                new ChallanService(
                        vehicleService);
    }

    @Test
    void testOverspeedingFineBelow10() {

        Violation violation =
                new Violation(
                        "V001",
                        "TN58AB1234",
                        ViolationType.OVERSPEEDING,
                        "Madurai",
                        LocalDateTime.now(),
                        70,
                        60);

        violationService.recordViolation(
                violation);

        Challan challan =
                challanService
                        .generateChallan(violation);

        assertEquals(
                500,
                challan.getFineAmount());
    }

    @Test
    void testOverspeedingBoundary10() {

        Violation violation =
                new Violation(
                        "V002",
                        "TN58AB1234",
                        ViolationType.OVERSPEEDING,
                        "Madurai",
                        LocalDateTime.now(),
                        70,
                        60);

        violationService.recordViolation(
                violation);

        Challan challan =
                challanService
                        .generateChallan(violation);

        assertEquals(
                500,
                challan.getFineAmount());
    }

    @Test
    void testOverspeedingFineBetween11And20() {

        Violation violation =
                new Violation(
                        "V003",
                        "TN58AB1234",
                        ViolationType.OVERSPEEDING,
                        "Madurai",
                        LocalDateTime.now(),
                        80,
                        60);

        violationService.recordViolation(
                violation);

        Challan challan =
                challanService
                        .generateChallan(violation);

        assertEquals(
                1000,
                challan.getFineAmount());
    }

    @Test
    void testOverspeedingFineAbove20() {

        Violation violation =
                new Violation(
                        "V004",
                        "TN58AB1234",
                        ViolationType.OVERSPEEDING,
                        "Madurai",
                        LocalDateTime.now(),
                        100,
                        60);

        violationService.recordViolation(
                violation);

        Challan challan =
                challanService
                        .generateChallan(violation);

        assertEquals(
                2000,
                challan.getFineAmount());
    }

    @Test
    void testSignalViolationFine() {

        Violation violation =
                new Violation(
                        "V005",
                        "TN58AB1234",
                        ViolationType.SIGNAL_VIOLATION,
                        "Madurai",
                        LocalDateTime.now(),
                        0,
                        0);

        violationService.recordViolation(
                violation);

        Challan challan =
                challanService
                        .generateChallan(violation);

        assertEquals(
                1500,
                challan.getFineAmount());
    }

    @Test
    void testIllegalParkingFine() {

        Violation violation =
                new Violation(
                        "V006",
                        "TN58AB1234",
                        ViolationType.ILLEGAL_PARKING,
                        "Madurai",
                        LocalDateTime.now(),
                        0,
                        0);

        violationService.recordViolation(
                violation);

        Challan challan =
                challanService
                        .generateChallan(violation);

        assertEquals(
                500,
                challan.getFineAmount());
    }

    @Test
    void testOutstandingFine() {

        Violation violation =
                new Violation(
                        "V007",
                        "TN58AB1234",
                        ViolationType.SIGNAL_VIOLATION,
                        "Madurai",
                        LocalDateTime.now(),
                        0,
                        0);

        violationService.recordViolation(
                violation);

        challanService
                .generateChallan(violation);

        assertEquals(
                1500,
                challanService
                        .calculateTotalOutstandingFine());
    }

    @Test
    void testRepeatedViolationPenalty() {

        Violation first =
                new Violation(
                        "V008",
                        "TN58AB1234",
                        ViolationType.ILLEGAL_PARKING,
                        "Madurai",
                        LocalDateTime.now(),
                        0,
                        0);

        violationService.recordViolation(first);

        challanService.generateChallan(first);

        Violation second =
                new Violation(
                        "V009",
                        "TN58AB1234",
                        ViolationType.ILLEGAL_PARKING,
                        "Madurai",
                        LocalDateTime.now(),
                        0,
                        0);

        violationService.recordViolation(second);

        Challan secondChallan =
                challanService
                        .generateChallan(second);

        assertEquals(
                750,
                secondChallan.getFineAmount());
    }
}
