package com.eliascoelho911.efood.ms_orders.controller

import com.eliascoelho911.efood.ms_orders.dto.OrderRequestDto
import com.eliascoelho911.efood.ms_orders.dto.OrderResponseDto
import com.eliascoelho911.efood.ms_orders.service.OrderService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController @Autowired constructor(
    private val service: OrderService
) {
    @PostMapping
    @CircuitBreaker(name = "getAuthorization", fallbackMethod = "errorOnRegisterOrder")
    fun registerOrder(@RequestBody orderRequestDto: OrderRequestDto): ResponseEntity<OrderResponseDto> {
        return registerOrder(orderRequestDto, fallback = false)
    }

    fun errorOnRegisterOrder(
        @RequestBody orderRequestDto: OrderRequestDto,
        exception: Exception
    ): ResponseEntity<OrderResponseDto> {
        return registerOrder(orderRequestDto, fallback = true)
    }

    private fun registerOrder(orderRequestDto: OrderRequestDto, fallback: Boolean): ResponseEntity<OrderResponseDto> {
        return service.registerOrderAndRequestPaymentStatusUpdate(orderRequestDto, fallback).let { orderResponseDto ->
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderResponseDto)
        }
    }

    @GetMapping
    fun getAll(): List<OrderResponseDto> {
        return service.getAll()
    }
}