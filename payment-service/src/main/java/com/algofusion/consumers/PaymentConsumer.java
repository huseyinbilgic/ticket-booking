package com.algofusion.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.algofusion.common.dto.events.PlaceOrderResponse;
import com.algofusion.services.PaymentService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentService paymentService;

    @KafkaListener(topics = "order-event", groupId = "payment-group")
    public void consumeOrderEvent(PlaceOrderResponse orderResponse) {
        paymentService.processPayment(orderResponse);
    }
}
