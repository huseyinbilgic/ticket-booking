package com.algofusion.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.algofusion.common.dto.events.PlaceOrderResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, PlaceOrderResponse> kafkaTemplate;

    public void sendOrder(PlaceOrderResponse orderCreatedEvent){
        kafkaTemplate.send("order-event", orderCreatedEvent);
    }
}
