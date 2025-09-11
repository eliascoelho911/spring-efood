package com.eliascoelho911.efood.ms_orders.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
) {
    @Column(name = "description", nullable = false)
    var description: String? = null

    @Column(name = "unitValue", nullable = false)
    var unitValue: BigDecimal? = null

    @Column(name = "amount", nullable = false)
    var amount: Int? = null

    @ManyToOne(targetEntity = Order::class)
    @JsonIgnore
    var order: Order? = null

    @get:Column(name = "totalValue", nullable = false)
    val totalValue: BigDecimal
        get() = unitValue?.multiply(amount?.toBigDecimal() ?: BigDecimal.ZERO) ?: BigDecimal.ZERO
}