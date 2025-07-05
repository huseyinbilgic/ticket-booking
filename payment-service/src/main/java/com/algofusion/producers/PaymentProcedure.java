package com.algofusion.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.algofusion.common.dto.events.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentProcedure {
    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    public void sendPayment(PaymentCompletedEvent paymentCompletedEvent){
        kafkaTemplate.send("payment-event", paymentCompletedEvent);
    }
}
