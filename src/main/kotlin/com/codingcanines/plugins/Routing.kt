package com.codingcanines.plugins

import com.codingcanines.repositories.users.UserRepository
import com.codingcanines.routes.authRoutes
import com.codingcanines.routes.userRoutes
import io.ktor.http.*
import io.ktor.openapi.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.di.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.openapi.*

fun Application.configureRouting() {
    val userRepository: UserRepository by dependencies

    val isDevMode = developmentMode

    routing {
        get("/") {
            call.respondText("gn fd\n- Puppy")
        }

        if (isDevMode) {
            swaggerUI(path = "swagger") {
                info = OpenApiInfo("GamePlanner API", "1.0")
                source = OpenApiDocSource.Routing(ContentType.Application.Json) {
                    routingRoot.descendants()
                }
            }
        }

        authRoutes(userRepository = userRepository)

        authenticate("auth-jwt") {
            userRoutes(userRepository = userRepository)
        }
    }
}
