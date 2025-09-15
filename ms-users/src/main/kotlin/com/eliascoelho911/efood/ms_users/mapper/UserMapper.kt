package com.eliascoelho911.efood.ms_users.mapper

import com.eliascoelho911.efood.ms_users.dto.UserDto
import com.eliascoelho911.efood.ms_users.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
interface UserMapper {
    fun toUserDto(user: User): UserDto
    @Mapping(target = "id", ignore = true)
    fun toBean(userDto: UserDto): User
}