package com.eliascoelho911.efood.ms_orders.mapper

import com.eliascoelho911.efood.ms_orders.dto.OrderRequestDto
import com.eliascoelho911.efood.ms_orders.dto.OrderResponseDto
import com.eliascoelho911.efood.ms_orders.model.Order
import org.mapstruct.Mapper
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
interface OrderMapper {
    fun toRequestDto(order: Order): OrderRequestDto
    fun toResponseDto(order: Order): OrderResponseDto

    fun toBean(orderRequestDto: OrderRequestDto): Order
    fun toBean(orderResponseDto: OrderResponseDto): Order
}