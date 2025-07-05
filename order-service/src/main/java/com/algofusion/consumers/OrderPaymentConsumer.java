package com.algofusion.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.algofusion.common.dto.events.PaymentCompletedEvent;
import com.algofusion.common.enums.Status;
import com.algofusion.services.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderPaymentConsumer {
    public final OrderService orderService;

    @KafkaListener(topics = "payment-event", groupId = "order-group")
    public void consumePaymentEvent(PaymentCompletedEvent paymentCompletedEvent) {
        orderService.updateOrderStatus(paymentCompletedEvent.getOrderId(), Status.PAID);
    }
}
