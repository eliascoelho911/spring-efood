package com.eliascoelho911.efood.ms_orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class MsOrdersApplication

fun main(args: Array<String>) {
	runApplication<MsOrdersApplication>(*args)
}
