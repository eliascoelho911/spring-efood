package com.eliascoelho911.efood.ms_orders.dto

import java.math.BigDecimal

data class OrderItemRequestDto(
    val description: String,
    val unitValue: BigDecimal,
    val amount: Int
)