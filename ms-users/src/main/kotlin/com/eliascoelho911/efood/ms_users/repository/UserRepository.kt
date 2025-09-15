package com.eliascoelho911.efood.ms_users.repository

import com.eliascoelho911.efood.ms_users.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByCpf(cpf: String): User?
}
