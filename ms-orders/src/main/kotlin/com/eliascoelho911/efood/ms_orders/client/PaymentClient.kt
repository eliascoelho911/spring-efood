package com.eliascoelho911.efood.ms_orders.client

import com.eliascoelho911.efood.ms_orders.config.FeignConfig
import com.eliascoelho911.efood.ms_orders.dto.AuthorizationDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("ms-payments", configuration = [FeignConfig::class])
interface PaymentClient {
    @GetMapping("/payments/authorization/{id}")
    fun getAuthorization(@PathVariable id: String): AuthorizationDto
}