package com.eliascoelho911.efood.ms_orders.controller

import com.eliascoelho911.efood.ms_orders.dto.OrderRequestDto
import com.eliascoelho911.efood.ms_orders.dto.OrderResponseDto
import com.eliascoelho911.efood.ms_orders.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController @Autowired constructor(
    private val service: OrderService
) {
    @PostMapping
    fun registerOrder(@RequestBody orderRequestDto: OrderRequestDto): ResponseEntity<OrderResponseDto> {
        return service.registerOrder(orderRequestDto).let { orderResponseDto ->
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