package com.codingcanines.models.dto.responses

import com.codingcanines.models.User
import com.codingcanines.models.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val username: String,
    val email: String,
    val role: UserRole
)

fun User.toUserResponse(): UserResponse = UserResponse(
    id = id,
    username = username,
    email = email,
    role = role
)
