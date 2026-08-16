package com.codingcanines.plugins

import com.codingcanines.repositories.users.UserRepository
import com.codingcanines.routes.authRoutes
import com.codingcanines.routes.userRoutes
import io.ktor.http.ContentType
import io.ktor.openapi.OpenApiInfo
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.di.*
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.openapi.OpenApiDocSource

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

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        authRoutes(userRepository = userRepository)

        authenticate("auth-jwt") {
            userRoutes(userRepository = userRepository)
        }
    }
}
