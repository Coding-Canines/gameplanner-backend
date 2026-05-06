package com.codingcanines.repositories

import com.codingcanines.models.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun findByUsernameOrEmail(username: String, email: String): User?
    suspend fun findByUsername(username: String): User?
    suspend fun addUser(username: String, email: String, passwordHash: String): User
}
