package com.codingcanines.models.users

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
    val role: UserRole
)