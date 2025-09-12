package com.eliascoelho911.efood.ms_payments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class MsPaymentsApplication

fun main(args: Array<String>) {
	runApplication<MsPaymentsApplication>(*args)
}
