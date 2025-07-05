package com.algofusion.common.dto.events;

import java.math.BigDecimal;

import com.algofusion.common.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderResponse {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private Status status;
}
