package com.eliascoelho911.efood.ms_orders.dto

import com.eliascoelho911.efood.ms_orders.model.OrderItem

data class OrderRequestDto(
    val cpf: String,
    val items: List<OrderItem>
)
