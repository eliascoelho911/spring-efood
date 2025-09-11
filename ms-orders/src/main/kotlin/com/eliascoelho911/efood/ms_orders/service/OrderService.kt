package com.eliascoelho911.efood.ms_orders.service

import com.eliascoelho911.efood.ms_orders.dto.OrderRequestDto
import com.eliascoelho911.efood.ms_orders.dto.OrderResponseDto
import com.eliascoelho911.efood.ms_orders.mapper.OrderMapper
import com.eliascoelho911.efood.ms_orders.model.Order
import com.eliascoelho911.efood.ms_orders.repository.OrderRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OrderService @Autowired constructor(
    private val repository: OrderRepository,
    private val mapper: OrderMapper = Mappers.getMapper(OrderMapper::class.java)
) {
    fun registerOrder(orderDto: OrderRequestDto): OrderResponseDto {
        val savedOrder = mapper.toBean(orderDto).apply {
            status = Order.Status.WAITING_FOR_PAYMENT
            date = LocalDate.now()
        }.let { order -> repository.save(order) }

        return mapper.toResponseDto(savedOrder)
    }

    fun getAll(): List<OrderResponseDto> {
        return repository.findAll()
            .map { mapper.toResponseDto(it) }
    }
}