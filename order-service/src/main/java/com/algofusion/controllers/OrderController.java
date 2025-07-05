package com.algofusion.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algofusion.common.dto.events.PlaceOrderResponse;
import com.algofusion.common.services.security.JwtUtil;
import com.algofusion.requests.PlaceOrderRequest;
import com.algofusion.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<PlaceOrderResponse> placeOrder(
            @Valid @RequestBody PlaceOrderRequest placeOrderRequest,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(userId, placeOrderRequest));
    }

}
