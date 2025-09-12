package com.eliascoelho911.efood.ms_payments.dto

data class AuthorizationDto(
    val orderId: String,
    val status: String
)