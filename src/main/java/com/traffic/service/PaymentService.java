package com.traffic.service;

import com.traffic.model.Challan;
import com.traffic.model.PaymentStatus;

public class PaymentService {

    private final ChallanService challanService;

    public PaymentService(
            ChallanService challanService) {

        this.challanService = challanService;
    }

    public void payChallan(
            String challanId) {

        Challan challan =
                challanService.getChallan(
                        challanId);

        if (challan.getPaymentStatus()
                == PaymentStatus.PAID) {

            throw new IllegalStateException(
                    "Challan is already paid: "
                            + challanId);
        }

        challan.markAsPaid();
    }

    public boolean isPaid(
            String challanId) {

        return challanService
                .getChallan(challanId)
                .getPaymentStatus()
                == PaymentStatus.PAID;
    }
}
