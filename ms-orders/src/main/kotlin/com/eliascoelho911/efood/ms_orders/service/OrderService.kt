package com.eliascoelho911.efood.ms_orders.service

import com.eliascoelho911.efood.ms_orders.client.PaymentClient
import com.eliascoelho911.efood.ms_orders.dto.OrderRequestDto
import com.eliascoelho911.efood.ms_orders.dto.OrderResponseDto
import com.eliascoelho911.efood.ms_orders.mapper.OrderMapper
import com.eliascoelho911.efood.ms_orders.model.Order
import com.eliascoelho911.efood.ms_orders.model.Order.Status
import com.eliascoelho911.efood.ms_orders.repository.OrderRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class OrderService @Autowired constructor(
    private val repository: OrderRepository,
    private val paymentClient: PaymentClient,
    private val mapper: OrderMapper = Mappers.getMapper(OrderMapper::class.java)
) {
    fun registerOrderAndRequestPaymentStatusUpdate(orderDto: OrderRequestDto, fallback: Boolean): OrderResponseDto {
        val savedOrder = registerOrder(orderDto).also {
            requestPaymentStatusUpdate(it, fallback)
        }

        return mapper.toResponseDto(savedOrder)
    }

    @Transactional
    fun registerOrder(orderDto: OrderRequestDto): Order {
        return mapper.toBean(orderDto).apply {
            status = Status.WAITING_FOR_PAYMENT
            date = LocalDate.now()
        }.let { order -> repository.save(order) }
    }

    @Transactional
    fun requestPaymentStatusUpdate(order: Order, fallback: Boolean) {
        order.status = getPaymentStatus(order.id!!.toString(), fallback)
        repository.save(order)
    }

    fun getAll(): List<OrderResponseDto> {
        return repository.findAll().map { mapper.toResponseDto(it) }
    }

    private fun getPaymentStatus(id: String, fallback: Boolean): Status {
        if (fallback) {
            return Status.PAYMENT_QUERY_ERROR
        }

        val authorization = paymentClient.getAuthorization(id)

        if (authorization.status.equals("AUTHORIZED", ignoreCase = true)) {
            return Status.PREPARING
        }

        return Status.REFUSED
    }
}