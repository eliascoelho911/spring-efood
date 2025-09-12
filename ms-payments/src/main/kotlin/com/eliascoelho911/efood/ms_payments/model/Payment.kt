package com.eliascoelho911.efood.ms_payments.model

import jakarta.persistence.*

@Entity
@Table(name = "payment_authorizations")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
) {
    @Column(name = "order_id", nullable = false)
    var orderId: String? = null

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: Status? = null

    enum class Status {
        AUTHORIZED,
        REFUSED
    }
}