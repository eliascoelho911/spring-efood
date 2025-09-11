package com.eliascoelho911.efood.ms_orders.repository

import com.eliascoelho911.efood.ms_orders.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderRepository: JpaRepository<Order, UUID>