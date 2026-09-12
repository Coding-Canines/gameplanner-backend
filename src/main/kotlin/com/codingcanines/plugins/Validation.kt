package com.codingcanines.plugins

import com.codingcanines.models.users.dto.requests.UserDetailsUpdateRequest
import io.konform.validation.Invalid
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<UserDetailsUpdateRequest> { request ->
            val result = request.validate()

            if (result is Invalid) {
                val reasons = result.errors.map { "${it.dataPath}: ${it.message}" }
                ValidationResult.Invalid(reasons)
            } else {
                ValidationResult.Valid
            }
        }
    }
}
