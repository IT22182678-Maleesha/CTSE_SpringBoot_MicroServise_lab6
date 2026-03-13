package com.lab_6.inventory.consumer;

import com.lab_6.inventory.model.OrderEvent;
import com.lab_6.inventory.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

    private final InventoryService inventoryService;

    public InventoryConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(OrderEvent event) {
        inventoryService.updateStock(event);
    }
}