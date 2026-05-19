package com.codingcanines.routes

import com.codingcanines.models.users.dto.responses.toUserResponse
import com.codingcanines.repositories.users.UserRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userRepository: UserRepository) {
    route("users") {
        get {
            val users = userRepository.getAllUsers()
            call.respond(HttpStatusCode.OK, users.map { it.toUserResponse() })
        }
    }
}
