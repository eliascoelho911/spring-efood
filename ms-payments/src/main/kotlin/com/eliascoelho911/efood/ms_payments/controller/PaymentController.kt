package com.eliascoelho911.efood.ms_payments.controller

import com.eliascoelho911.efood.ms_payments.dto.AuthorizationDto
import com.eliascoelho911.efood.ms_payments.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentController @Autowired constructor(
    private val service: PaymentService
) {
    @GetMapping("/authorization/{orderId}")
    fun getPaymentAuthorization(@PathVariable orderId: String): AuthorizationDto {
        return service.authorizePayment(orderId)
    }

    @GetMapping
    fun getAll(): List<AuthorizationDto> {
        return service.getAll()
    }
}