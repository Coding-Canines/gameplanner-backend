package com.codingcanines.routes

import com.codingcanines.models.users.dto.responses.toUserResponse
import com.codingcanines.repositories.users.UserRepository
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userRepository: UserRepository) {
    route("users") {
        get {
            val users = userRepository.getAllUsers()
            call.respond(HttpStatusCode.OK, users.map { it.toUserResponse() })
        }

        get("me") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid token claims")
                return@get
            }

            val user = userRepository.findById(userId)

            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
                return@get
            }

            call.respond(HttpStatusCode.OK, user.toUserResponse())
        }
    }
}
