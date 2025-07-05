package com.algofusion.mapper;

import org.mapstruct.Mapper;

import com.algofusion.common.dto.events.PlaceOrderResponse;
import com.algofusion.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    
    PlaceOrderResponse toPlaceOrderResponse(Order order);
}
