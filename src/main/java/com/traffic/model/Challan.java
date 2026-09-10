package com.traffic.model;

import java.time.LocalDateTime;

public class Challan {

    private String challanId;
    private Violation violation;
    private double fineAmount;
    private PaymentStatus paymentStatus;
    private LocalDateTime generatedAt;

    public Challan(
            String challanId,
            Violation violation,
            double fineAmount) {

        this.challanId = challanId;
        this.violation = violation;
        this.fineAmount = fineAmount;

        this.paymentStatus = PaymentStatus.UNPAID;
        this.generatedAt = LocalDateTime.now();
    }

    public String getChallanId() {
        return challanId;
    }

    public Violation getViolation() {
        return violation;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void markAsPaid() {
        this.paymentStatus = PaymentStatus.PAID;
    }

    @Override
    public String toString() {

        return "Challan{" +
                "challanId='" + challanId + '\'' +
                ", vehicleNumber='" +
                violation.getVehicleNumber() + '\'' +
                ", violationType=" +
                violation.getViolationType() +
                ", location='" +
                violation.getLocation() + '\'' +
                ", fineAmount=" +
                fineAmount +
                ", paymentStatus=" +
                paymentStatus +
                ", generatedAt=" +
                generatedAt +
                '}';
    }
}
