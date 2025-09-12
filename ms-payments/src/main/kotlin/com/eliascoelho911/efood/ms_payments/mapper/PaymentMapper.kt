package com.eliascoelho911.efood.ms_payments.mapper

import com.eliascoelho911.efood.ms_payments.dto.AuthorizationDto
import com.eliascoelho911.efood.ms_payments.model.Payment
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
interface PaymentMapper {
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "status", expression = "java(payment.getStatus().name())")
    fun toAuthorizationDto(payment: Payment): AuthorizationDto
}