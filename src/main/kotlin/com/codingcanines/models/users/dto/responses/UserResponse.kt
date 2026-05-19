package com.codingcanines.models.users.dto.responses

import com.codingcanines.models.users.User
import com.codingcanines.models.users.UserRole
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
