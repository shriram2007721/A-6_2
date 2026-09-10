package com.traffic.model;

import java.time.LocalDateTime;

public class Violation {

    private String violationId;
    private String vehicleNumber;
    private ViolationType violationType;
    private String location;
    private LocalDateTime timestamp;
    private double speed;
    private double permittedSpeed;

    public Violation(
            String violationId,
            String vehicleNumber,
            ViolationType violationType,
            String location,
            LocalDateTime timestamp,
            double speed,
            double permittedSpeed) {

        this.violationId = violationId;
        this.vehicleNumber = vehicleNumber;
        this.violationType = violationType;
        this.location = location;
        this.timestamp = timestamp;
        this.speed = speed;
        this.permittedSpeed = permittedSpeed;
    }

    public String getViolationId() {
        return violationId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPermittedSpeed() {
        return permittedSpeed;
    }

    @Override
    public String toString() {

        return "Violation{" +
                "violationId='" + violationId + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", violationType=" + violationType +
                ", location='" + location + '\'' +
                ", timestamp=" + timestamp +
                ", speed=" + speed +
                ", permittedSpeed=" + permittedSpeed +
                '}';
    }
}
