package com.eliascoelho911.efood.ms_orders.dto

data class AuthorizationDto(
    val orderId: String,
    val status: String
)