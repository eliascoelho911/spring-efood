package com.eliascoelho911.efood.ms_orders.dto

data class OrderRequestDto(
    val cpf: String,
    val items: List<OrderItemRequestDto>
)
