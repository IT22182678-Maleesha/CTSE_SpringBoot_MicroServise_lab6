package com.lab_6.billing.consumer;

import com.lab_6.billing.model.OrderEvent;
import com.lab_6.billing.service.BillingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BillingConsumer {

    private final BillingService billingService;

    public BillingConsumer(BillingService billingService) {
        this.billingService = billingService;
    }

    @KafkaListener(topics = "order-topic", groupId = "billing-group")
    public void consume(OrderEvent event) {
        billingService.generateInvoice(event);
    }
}