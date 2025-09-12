package com.eliascoelho911.efood.ms_payments.service

import com.eliascoelho911.efood.ms_payments.dto.AuthorizationDto
import com.eliascoelho911.efood.ms_payments.mapper.PaymentMapper
import com.eliascoelho911.efood.ms_payments.model.AuthorizationGenerator
import com.eliascoelho911.efood.ms_payments.model.Payment
import com.eliascoelho911.efood.ms_payments.repository.PaymentRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PaymentService @Autowired constructor(
    private val repository: PaymentRepository,
    private val mapper: PaymentMapper = Mappers.getMapper(PaymentMapper::class.java)
) {
    fun authorizePayment(orderId: String): AuthorizationDto {
        val payment = Payment().apply {
            this.orderId = orderId
            this.status = if (AuthorizationGenerator.getRandomBoolean()) {
                Payment.Status.AUTHORIZED
            } else {
                Payment.Status.REFUSED
            }
        }
        
        val savedPayment = repository.save(payment)
        return mapper.toAuthorizationDto(savedPayment)
    }

    fun getAll(): List<AuthorizationDto> {
        return repository.findAll()
            .map { mapper.toAuthorizationDto(it) }
    }
}