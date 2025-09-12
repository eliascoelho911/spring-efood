package com.eliascoelho911.efood.ms_payments.model

import kotlin.random.Random

object AuthorizationGenerator {
    fun getRandomBoolean(): Boolean {
        return Random.nextBoolean()
    }
}