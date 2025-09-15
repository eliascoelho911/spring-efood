package com.eliascoelho911.efood.ms_users.service

import com.eliascoelho911.efood.ms_users.dto.MailDto
import com.eliascoelho911.efood.ms_users.dto.UserDto
import com.eliascoelho911.efood.ms_users.mapper.UserMapper
import com.eliascoelho911.efood.ms_users.repository.UserRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val repository: UserRepository,
    private val mailSender: JavaMailSender,
    private val userMapper: UserMapper = Mappers.getMapper(UserMapper::class.java)
) {
    fun getAll(): List<UserDto> {
        return repository.findAll()
            .map { userMapper.toUserDto(it) }
    }

    fun registerUser(userDto: UserDto): UserDto {
        return userMapper.toBean(userDto)
            .also { repository.save(it) }
            .let { userMapper.toUserDto(it) }
    }

    fun sendMessage(mailDto: MailDto) {
        repository.findByCpf(mailDto.cpf!!)?.let { user ->
            user.mail?.let { userMail ->
                SimpleMailMessage().apply {
                    setTo(userMail)
                    subject = "Status do pedido ${mailDto.orderId}"
                    text = "O pedido está: ${mailDto.status}"
                }.also { mailMessage ->
                    mailSender.send(mailMessage)
                    println("Mensagem enviada com sucesso!")
                }
            } ?: run {
                throw IllegalArgumentException("Erro ao enviar mensagem: Usuário sem email cadastrado")
            }
        } ?: run {
            throw IllegalArgumentException("Erro ao enviar mensagem: Usuário com o CPF ${mailDto.cpf} não foi encontrado")
        }
    }
}