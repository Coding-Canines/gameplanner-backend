package com.codingcanines.repositories.users

import com.codingcanines.models.users.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun findByUsernameOrEmail(username: String, email: String): User?
    suspend fun findByUsername(username: String): User?
    suspend fun addUser(username: String, email: String, passwordHash: String): User
}