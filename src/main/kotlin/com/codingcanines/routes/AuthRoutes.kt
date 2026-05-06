package com.codingcanines.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.codingcanines.models.UserRole
import com.codingcanines.models.dto.requests.LoginRequest
import com.codingcanines.models.dto.requests.RegisterRequest
import com.codingcanines.models.dto.responses.toUserResponse
import com.codingcanines.repositories.UserRepository
import com.codingcanines.utils.PasswordHelper
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.util.Date

fun Route.authRoutes(userRepository: UserRepository) {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()

    post("/login") {
        val request = call.receive<LoginRequest>()

        val user = userRepository.findByUsername(request.username)
        if (user == null || !PasswordHelper.verifyPassword(request.password, user.passwordHash)) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
            return@post
        }

        val token = JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", user.username)
            .withClaim("role", UserRole.Staff.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000L))
            .sign(Algorithm.HMAC256(jwtSecret))

        call.respond(HttpStatusCode.OK, mapOf("token" to token))
    }

    post("/register") {
        val request = call.receive<RegisterRequest>()

        val existingUser = userRepository.findByUsernameOrEmail(request.username, request.email)
        if (existingUser != null) {
            call.respond(HttpStatusCode.Conflict, "Username or email already taken")
            return@post
        }

        val hashedPassword = PasswordHelper.hashPassword(request.password)
        val createdUser = userRepository.addUser(request.username, request.email, hashedPassword)
        call.respond(HttpStatusCode.Created, createdUser.toUserResponse())
    }
}
