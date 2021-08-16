package com.example.orderManagement

import com.example.base.DatabaseFactory
import com.example.orderManagement.product.product
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }
    DatabaseFactory.init()
    product()
}