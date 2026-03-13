package com.lab_6.order.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.lab_6.order.model.OrderEvent;

@Service
public class OrderProducerService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducerService(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrder(OrderEvent orderEvent) {
        kafkaTemplate.send("order-topic", orderEvent.getOrderId(), orderEvent);
        System.out.println("Order event published: " + orderEvent.getOrderId());
    }
}