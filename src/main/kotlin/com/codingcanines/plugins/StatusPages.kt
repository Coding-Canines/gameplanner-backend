package com.codingcanines.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("errors" to cause.reasons))
        }

        exception<JsonConvertException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "Invalid JSON payload or date format")
        }
    }
}
