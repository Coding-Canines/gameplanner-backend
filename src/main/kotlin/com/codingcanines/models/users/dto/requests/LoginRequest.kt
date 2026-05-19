package com.codingcanines.models.users.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean
)