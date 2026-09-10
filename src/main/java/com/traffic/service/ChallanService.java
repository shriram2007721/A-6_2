package com.traffic.service;

import com.traffic.exception.ChallanNotFoundException;
import com.traffic.model.Challan;
import com.traffic.model.PaymentStatus;
import com.traffic.model.Vehicle;
import com.traffic.model.Violation;
import com.traffic.model.ViolationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChallanService {

    private final VehicleService vehicleService;

    private final Map<String, Challan> challans =
            new HashMap<>();

    public ChallanService(
            VehicleService vehicleService) {

        this.vehicleService = vehicleService;
    }

    public Challan generateChallan(
            Violation violation) {

        if (violation == null) {

            throw new IllegalArgumentException(
                    "Violation cannot be null");
        }

        String challanId =
                "CH-" + violation.getViolationId();

        if (challans.containsKey(challanId)) {

            throw new IllegalArgumentException(
                    "Challan already generated for violation: "
                            + violation.getViolationId());
        }

        Vehicle vehicle =
                vehicleService.getVehicle(
                        violation.getVehicleNumber());

        int previousViolations =
                vehicle.getViolationCount();

        double baseFine =
                calculateBaseFine(violation);

        double multiplier;

        if (previousViolations <= 1) {
            multiplier = 1.0;
        } else if (previousViolations == 2) {
            multiplier = 1.5;
        } else {
            multiplier = 2.0;
        }

        double finalFine =
                baseFine * multiplier;

        Challan challan =
                new Challan(
                        challanId,
                        violation,
                        finalFine);

        challans.put(challanId, challan);

        return challan;
    }

    private double calculateBaseFine(
            Violation violation) {

        if (violation.getViolationType()
                == ViolationType.OVERSPEEDING) {

            double excess =
                    violation.getSpeed()
                            - violation.getPermittedSpeed();

            if (excess <= 10) {
                return 500;
            } else if (excess <= 20) {
                return 1000;
            } else {
                return 2000;
            }
        }

        if (violation.getViolationType()
                == ViolationType.SIGNAL_VIOLATION) {

            return 1500;
        }

        if (violation.getViolationType()
                == ViolationType.ILLEGAL_PARKING) {

            return 500;
        }

        return 0;
    }

    public Challan getChallan(
            String challanId) {

        Challan challan =
                challans.get(challanId);

        if (challan == null) {

            throw new ChallanNotFoundException(
                    "Challan not found: " + challanId);
        }

        return challan;
    }

    public List<Challan> getAllChallans() {

        return new ArrayList<>(
                challans.values());
    }

    public List<Challan> getUnpaidChallans() {

        List<Challan> result =
                new ArrayList<>();

        for (Challan challan :
                challans.values()) {

            if (challan.getPaymentStatus()
                    == PaymentStatus.UNPAID) {

                result.add(challan);
            }
        }

        return result;
    }

    public List<Challan> getPaidChallans() {

        List<Challan> result =
                new ArrayList<>();

        for (Challan challan :
                challans.values()) {

            if (challan.getPaymentStatus()
                    == PaymentStatus.PAID) {

                result.add(challan);
            }
        }

        return result;
    }

    public double calculateTotalOutstandingFine() {

        double total = 0;

        for (Challan challan :
                challans.values()) {

            if (challan.getPaymentStatus()
                    == PaymentStatus.UNPAID) {

                total += challan.getFineAmount();
            }
        }

        return total;
    }
}
