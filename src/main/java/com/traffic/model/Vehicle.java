package com.traffic.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private String vehicleNumber;
    private String ownerName;
    private String ownerPhone;
    private VehicleType vehicleType;

    private List<Violation> violationHistory;

    public Vehicle(
            String vehicleNumber,
            String ownerName,
            String ownerPhone,
            VehicleType vehicleType) {

        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.vehicleType = vehicleType;

        this.violationHistory = new ArrayList<>();
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public List<Violation> getViolationHistory() {
        return violationHistory;
    }

    public void addViolation(Violation violation) {
        violationHistory.add(violation);
    }

    public int getViolationCount() {
        return violationHistory.size();
    }

    public String classifyVehicle() {

        int count = violationHistory.size();

        if (count == 0) {
            return "SAFE";
        }

        if (count <= 2) {
            return "LOW RISK";
        }

        if (count <= 5) {
            return "MEDIUM RISK";
        }

        return "HIGH RISK";
    }

    @Override
    public String toString() {

        return "Vehicle{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", vehicleType=" + vehicleType +
                ", violations=" + violationHistory.size() +
                '}';
    }
}
