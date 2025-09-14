package com.eliascoelho911.efood.ms_users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class MsUsersApplication

fun main(args: Array<String>) {
	runApplication<MsUsersApplication>(*args)
}
