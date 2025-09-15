package com.eliascoelho911.efood.ms_users.config

import com.eliascoelho911.efood.ms_users.dto.MailDto
import com.eliascoelho911.efood.ms_users.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.annotation.Payload

@Configuration
class MailConfig @Autowired constructor(
    private val service: UserService
) {
    @Value("queue.message.user")
    private lateinit var queue: String

    @Bean
    fun queue(): Queue =
        Queue(queue, true)

    @RabbitListener(queues = ["queue.message.user"])
    private fun sendMail(@Payload message: MailDto) {
        println(message)
        service.sendMessage(message)
    }

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter(ObjectMapper())
    }
}