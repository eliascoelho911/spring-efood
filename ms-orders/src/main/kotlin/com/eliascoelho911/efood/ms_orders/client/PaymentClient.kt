package com.eliascoelho911.efood.ms_orders.client

import com.eliascoelho911.efood.ms_orders.dto.AuthorizationDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient("ms-payments")
interface PaymentClient {
    @GetMapping("/payments/authorization/{id}")
    fun getAuthorization(@PathVariable id: String): AuthorizationDto
}