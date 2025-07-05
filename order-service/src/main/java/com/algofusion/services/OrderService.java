package com.algofusion.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algofusion.common.dto.GlobalException;
import com.algofusion.common.dto.events.PlaceOrderResponse;
import com.algofusion.common.enums.Status;
import com.algofusion.entities.Event;
import com.algofusion.entities.Order;
import com.algofusion.mapper.OrderMapper;
import com.algofusion.producers.OrderProducer;
import com.algofusion.repositories.OrderRepository;
import com.algofusion.requests.PlaceOrderRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventService eventService;
    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;

    public PlaceOrderResponse placeOrder(Long userId, PlaceOrderRequest placeOrderRequest) {
        Event byId = eventService.findById(placeOrderRequest.getEventId());
        Order order = Order.builder()
                .quantity(placeOrderRequest.getQuantity())
                .userId(userId)
                .status(Status.PENDING)
                .event(byId)
                .totalPrice(byId.getUnitPrice().multiply(BigDecimal.valueOf(placeOrderRequest.getQuantity())))
                .build();

        orderRepository.save(order);
        PlaceOrderResponse placeOrderResponse = orderMapper.toPlaceOrderResponse(order);
        orderProducer.sendOrder(placeOrderResponse);
        return placeOrderResponse;
    }

    public void updateOrderStatus(Long id, Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Order not found by id : " + id));

        order.setStatus(status);
    }
}
