package com.eliascoelho911.efood.ms_users.controller

import com.eliascoelho911.efood.ms_users.dto.UserDto
import com.eliascoelho911.efood.ms_users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
    private val service: UserService
) {
    @GetMapping
    fun getAll(): List<UserDto> {
        return service.getAll()
    }

    @PostMapping
    fun registerUser(@RequestBody user: UserDto): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.registerUser(user))
    }
}
