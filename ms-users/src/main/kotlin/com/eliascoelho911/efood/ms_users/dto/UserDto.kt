package com.eliascoelho911.efood.ms_users.dto

data class UserDto(
    val id: Long? = null,
    val cpf: String,
    val name: String,
    val mail: String
)
