package com.codingcanines.plugins

import com.codingcanines.repositories.UserRepository
import com.codingcanines.routes.authRoutes
import com.codingcanines.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.di.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val userRepository: UserRepository by dependencies

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        authRoutes(userRepository = userRepository)

        authenticate("auth-jwt") {
            userRoutes(userRepository = userRepository)
        }
    }
}
