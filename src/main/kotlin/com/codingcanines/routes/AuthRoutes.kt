package com.codingcanines.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.codingcanines.models.users.dto.requests.LoginRequest
import com.codingcanines.models.users.dto.requests.RegisterRequest
import com.codingcanines.models.users.dto.responses.toUserResponse
import com.codingcanines.repositories.users.UserRepository
import com.codingcanines.utils.PasswordHelper
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.util.Date
import java.util.UUID

fun Route.authRoutes(userRepository: UserRepository) {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()

    val algorithm = Algorithm.HMAC256(jwtSecret)

    post("/login") {
        val request = call.receive<LoginRequest>()

        val user = userRepository.findByUsername(request.username)
        if (user == null || !PasswordHelper.verifyPassword(request.password, user.passwordHash)) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
            return@post
        }

        val now = System.currentTimeMillis()

        val accessToken = JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", user.username)
            .withClaim("role", user.role.toString())
            .withExpiresAt(Date(now + 15 * 60 * 1000L))
            .sign(algorithm)

        val refreshLifespan = if (request.rememberMe) {
            30 * 24 * 60 * 60 * 1000L
        } else {
            24 * 60 * 60 * 1000L
        }

        val refreshTokenId = UUID.randomUUID().toString()
        val refreshToken = JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", user.username)
            .withJWTId(refreshTokenId)
            .withExpiresAt(Date(now + refreshLifespan))
            .sign(algorithm)

        call.respond(HttpStatusCode.OK, mapOf("accessToken" to accessToken, "refreshToken" to refreshToken))
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
