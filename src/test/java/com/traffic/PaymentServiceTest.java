package com.traffic;

import com.traffic.exception.ChallanNotFoundException;
import com.traffic.model.Challan;
import com.traffic.model.PaymentStatus;
import com.traffic.model.Vehicle;
import com.traffic.model.VehicleType;
import com.traffic.model.Violation;
import com.traffic.model.ViolationType;
import com.traffic.service.ChallanService;
import com.traffic.service.PaymentService;
import com.traffic.service.VehicleService;
import com.traffic.service.ViolationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    private VehicleService vehicleService;
    private ViolationService violationService;
    private ChallanService challanService;
    private PaymentService paymentService;

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

        paymentService =
                new PaymentService(
                        challanService);
    }

    @Test
    void testChallanPayment() {

        Violation violation =
                new Violation(
                        "V100",
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
                PaymentStatus.UNPAID,
                challan.getPaymentStatus());

        paymentService.payChallan(
                challan.getChallanId());

        assertEquals(
                PaymentStatus.PAID,
                challan.getPaymentStatus());
    }

    @Test
    void testOutstandingFineAfterPayment() {

        Violation violation =
                new Violation(
                        "V101",
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

        paymentService.payChallan(
                challan.getChallanId());

        assertEquals(
                0,
                challanService
                        .calculateTotalOutstandingFine());
    }

    @Test
    void testInvalidChallanPayment() {

        assertThrows(
                ChallanNotFoundException.class,
                () -> paymentService
                        .payChallan("INVALID"));
    }

    @Test
    void testDoublePayment() {

        Violation violation =
                new Violation(
                        "V102",
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

        paymentService.payChallan(
                challan.getChallanId());

        assertThrows(
                IllegalStateException.class,
                () -> paymentService
                        .payChallan(
                                challan.getChallanId()));
    }
}
