package com.traffic;

import com.traffic.model.Challan;
import com.traffic.model.Vehicle;
import com.traffic.model.VehicleType;
import com.traffic.model.Violation;
import com.traffic.model.ViolationType;
import com.traffic.service.ChallanService;
import com.traffic.service.PaymentService;
import com.traffic.service.VehicleService;
import com.traffic.service.ViolationService;

import java.time.LocalDateTime;

public class App {

    public static void main(String[] args) {

        VehicleService vehicleService = new VehicleService();

        ViolationService violationService =
                new ViolationService(vehicleService);

        ChallanService challanService =
                new ChallanService(vehicleService);

        PaymentService paymentService =
                new PaymentService(challanService);

        System.out.println("======================================");
        System.out.println(" REAL-TIME E-CHALLAN MANAGEMENT SYSTEM");
        System.out.println("======================================");

        Vehicle vehicle = new Vehicle(
                "TN58AB1234",
                "Nishanth",
                "9876543210",
                VehicleType.CAR
        );

        vehicleService.registerVehicle(vehicle);

        System.out.println("\nVehicle Registered:");
        System.out.println(vehicle);

        Violation violation = new Violation(
                "V001",
                "TN58AB1234",
                ViolationType.OVERSPEEDING,
                "Madurai Main Road",
                LocalDateTime.now(),
                85,
                60
        );

        violationService.recordViolation(violation);

        Challan challan =
                challanService.generateChallan(violation);

        System.out.println("\nE-Challan Generated:");
        System.out.println(challan);

        System.out.println("\nOutstanding Fine: Rs. " +
                challanService.calculateTotalOutstandingFine());

        System.out.println("\nVehicle Classification: " +
                vehicle.classifyVehicle());

        paymentService.payChallan(challan.getChallanId());

        System.out.println("\nPayment Successful.");

        System.out.println("Payment Status: " +
                challan.getPaymentStatus());

        System.out.println("Outstanding Fine: Rs. " +
                challanService.calculateTotalOutstandingFine());

        System.out.println("\n======================================");
        System.out.println(" APPLICATION COMPLETED SUCCESSFULLY");
        System.out.println("======================================");
    }
}
