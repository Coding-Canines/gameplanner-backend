package com.codingcanines.models.auth.dto.responses

import com.codingcanines.models.users.dto.responses.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)
