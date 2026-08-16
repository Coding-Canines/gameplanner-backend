package com.codingcanines

import com.codingcanines.plugins.configureAuthentication
import com.codingcanines.plugins.configureCors
import com.codingcanines.plugins.configureDependencies
import com.codingcanines.plugins.configureMonitoring
import com.codingcanines.plugins.configureRouting
import com.codingcanines.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureDependencies()
    configureAuthentication()
    configureCors()
    configureRouting()
}
