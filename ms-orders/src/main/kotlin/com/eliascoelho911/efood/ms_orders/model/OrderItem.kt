package com.eliascoelho911.efood.ms_orders.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "items")
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItem

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}