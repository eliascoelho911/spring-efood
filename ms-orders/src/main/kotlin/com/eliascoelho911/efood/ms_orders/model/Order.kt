package com.eliascoelho911.efood.ms_orders.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @Column(name = "cpf", nullable = false)
    var cpf: String? = null

    @Column(name = "date", nullable = false)
    var date: LocalDate? = null

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: Status? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], targetEntity = OrderItem::class)
    var items: List<OrderItem>? = listOf()

    @get:Column(name = "totalValue", nullable = false)
    val totalValue: BigDecimal
        get() = items?.sumOf { it.totalValue } ?: BigDecimal.ZERO

    @PrePersist
    fun linkItems() {
        items?.forEach { it.order = this }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    enum class Status {
        WAITING_FOR_PAYMENT,
        PAYMENT_QUERY_ERROR,
        REFUSED,
        PREPARING,
        DELIVERY_OUT,
        DELIVERED
    }
}
