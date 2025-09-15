package com.eliascoelho911.efood.ms_users.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var cpf: String? = null
    var name: String? = null
    var mail: String? = null
}
