package com.eliascoelho911.efood.ms_orders.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: UUID? = null
) {
    @Column(name = "cpf", nullable = false)
    var cpf: String? = null

    @Column(name = "date", nullable = false)
    var date: LocalDate? = null

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: Status? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], targetEntity = OrderItem::class)
    var items: List<OrderItem>? = emptyList()
        set(value) {
            value?.forEach { item -> item.order = this@Order }
            field = value
        }

    @get:Column(name = "totalValue", nullable = false)
    val totalValue: BigDecimal
        get() = items?.sumOf { it.totalValue } ?: BigDecimal.ZERO

    enum class Status {
        WAITING_FOR_PAYMENT,
        PAYMENT_QUERY_ERROR,
        REFUSED,
        PREPARING,
        DELIVERY_OUT,
        DELIVERED
    }
}
