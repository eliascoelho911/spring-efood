package com.eliascoelho911.efood.ms_payments.repository

import com.eliascoelho911.efood.ms_payments.model.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, Long>