package com.codingcanines.repositories.users

import com.codingcanines.models.users.User
import com.codingcanines.models.users.UserDetails

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun findByUsernameOrEmail(username: String, email: String): User?
    suspend fun findByUsername(username: String): User?
    suspend fun findById(id: Int): User?
    suspend fun addUser(username: String, email: String, passwordHash: String): User
    suspend fun updateUserDetails(id: Int, userDetails: UserDetails): User?
}