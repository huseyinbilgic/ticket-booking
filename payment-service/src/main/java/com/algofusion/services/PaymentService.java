package com.algofusion.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algofusion.common.dto.events.PaymentCompletedEvent;
import com.algofusion.common.dto.events.PlaceOrderResponse;
import com.algofusion.entities.Payment;
import com.algofusion.producers.PaymentProcedure;
import com.algofusion.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    public final PaymentRepository paymentRepository;
    public final PaymentProcedure paymentProcedure;

    public void processPayment(PlaceOrderResponse placeOrderResponse) {
        Payment payment = Payment.builder()
                .orderId(placeOrderResponse.getId())
                .totalPrice(placeOrderResponse.getTotalPrice())
                .build();
        paymentRepository.save(payment);

        paymentProcedure.sendPayment(PaymentCompletedEvent.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .totalPrice(payment.getTotalPrice())
                .build());
    }
}
