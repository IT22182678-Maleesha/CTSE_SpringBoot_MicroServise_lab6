package com.lab_6.inventory.service;

import com.lab_6.inventory.model.OrderEvent;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    public void updateStock(OrderEvent event) {
        System.out.println("Inventory Service received order: " + event.getOrderId());
        System.out.println("Updating stock for item: " + event.getItem()
                + ", quantity: " + event.getQuantity());
    }
}