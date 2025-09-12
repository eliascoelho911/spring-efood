package com.eliascoelho911.efood.ms_orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class MsOrdersApplication

fun main(args: Array<String>) {
	runApplication<MsOrdersApplication>(*args)
}
