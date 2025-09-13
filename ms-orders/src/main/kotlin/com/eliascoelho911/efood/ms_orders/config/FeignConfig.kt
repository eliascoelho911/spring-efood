package com.eliascoelho911.efood.ms_orders.config

import feign.Logger
import org.springframework.context.annotation.Bean

class FeignConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.BASIC
    }
}