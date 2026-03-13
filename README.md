# CTSE_SpringBoot_MicroServise_lab6

This repository contains a microservices-based application built with Spring Boot, demonstrating event-driven architecture using Apache Kafka and API Gateway pattern.

Project Structure
The application consists of four microservices:

API Gateway (Port: 8080) - Routes requests to appropriate services

Order Service (Port: 8081) - Handles order creation and publishes events

Inventory Service (Port: 8082) - Consumes order events and updates stock

Billing Service (Port: 8083) - Consumes order events and generates invoices

Kafka (Port: 9092) - Message broker for event communication

Architecture Diagram
text
Client Request → API Gateway (8080) → Order Service (8081) → Kafka → Inventory Service (8082)
                                                                  ↘ Billing Service (8083)
Prerequisites
Java 17 or higher

Docker and Docker Compose

Maven (or use included Maven wrapper)

Getting Started
1. Start Kafka
bash
# From the root directory
docker-compose up -d
2. Start the Services
Run each service in a separate terminal:

Order Service:

bash
cd order
./mvnw spring-boot:run
Inventory Service:

bash
cd inventory
./mvnw spring-boot:run
Billing Service:

bash
cd billing
./mvnw spring-boot:run
API Gateway:

bash
cd api-gateway
./mvnw spring-boot:run
3. Test the Application
Create an order by sending a POST request to the API Gateway:

bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "12345",
    "item": "Laptop",
    "quantity": 1
  }'
Expected response:

text
Order Created & Event Published
4. Verify Event Processing
Check the console outputs for each service:

Order Service Output:

text
Order event published: 12345
Inventory Service Output:

text
Inventory Service received order: 12345
Updating stock for item: Laptop, quantity: 1
Billing Service Output:

text
Billing Service received order: 12345
Generating invoice for item: Laptop, quantity: 1
Service Details
API Gateway (Port: 8080)
Routes /orders/** requests to Order Service

Configuration: api-gateway/src/main/resources/application.yml

Order Service (Port: 8081)
REST endpoint: POST /orders

Publishes order events to Kafka topic: order-topic

Inventory Service (Port: 8082)
Consumes events from order-topic

Updates inventory based on order items

Billing Service (Port: 8083)
Consumes events from order-topic

Generates invoices for orders

Kafka Configuration
Single-node Kafka setup

Topic: order-topic (auto-created)

No ZooKeeper required (KRaft mode)

Configuration Files
Docker Compose (docker-compose.yml)
yaml
services:
  kafka:
    image: confluentinc/cp-kafka:7.6.0
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka:9093
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      CLUSTER_ID: MkU3OEVBNTcwNTJENDM2Qk
Service Configuration Examples
Order Service (order/src/main/resources/application.yml):

yaml
server:
  port: 8081

spring:
  application:
    name: order-service
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JacksonJsonSerializer
      properties:
        '[spring.json.add.type.headers]': false
Consumer Service (billing/src/main/resources/application.yml):

yaml
server:
  port: 8083

spring:
  application:
    name: billing-service
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: billing-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JacksonJsonDeserializer
      properties:
        '[spring.json.trusted.packages]': "*"
        '[spring.json.value.default.type]': com.lab_6.billing.model.OrderEvent
        '[spring.json.use.type.headers]': false
Dependencies
All services use Spring Boot 4.0.3 and include:

Spring Web MVC

Spring Kafka

Lombok

Spring Boot Actuator (API Gateway only)

Spring Cloud Gateway (API Gateway only)

Troubleshooting
Kafka Connection Issues
If services can't connect to Kafka, ensure:

Kafka container is running: docker ps

Kafka logs show no errors: docker logs kafka

Port 9092 is not blocked

Maven Build Issues
Use the included Maven wrapper:

bash
./mvnw clean install
Service Startup Order
Start services in this order:

Kafka

Order Service

Inventory Service

Billing Service

API Gateway
