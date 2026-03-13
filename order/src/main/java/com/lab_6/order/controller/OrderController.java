package com.lab_6.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab_6.order.model.OrderEvent;
import com.lab_6.order.service.OrderProducerService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducerService orderProducerService;

    public OrderController(OrderProducerService orderProducerService) {
        this.orderProducerService = orderProducerService;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderEvent orderEvent) {
        orderProducerService.publishOrder(orderEvent);
        return "Order Created & Event Published";
    }
}