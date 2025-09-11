package com.eliascoelho911.efood.ms_orders.dto

import com.eliascoelho911.efood.ms_orders.model.Order
import com.eliascoelho911.efood.ms_orders.model.OrderItem
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class OrderResponseDto(
    val id: UUID,
    val status: Order.Status,
    val cpf: String,
    val items: List<OrderItem>,
    val totalValue: BigDecimal,
    val date: LocalDate
)
