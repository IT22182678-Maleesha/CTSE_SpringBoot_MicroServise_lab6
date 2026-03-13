package com.lab_6.billing.service;

import com.lab_6.billing.model.OrderEvent;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    public void generateInvoice(OrderEvent event) {
        System.out.println("Billing Service received order: " + event.getOrderId());
        System.out.println("Generating invoice for item: " + event.getItem()
                + ", quantity: " + event.getQuantity());
    }
}