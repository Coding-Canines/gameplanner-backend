package com.codingcanines.database

import com.codingcanines.models.UserRole
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Users : IntIdTable("users") {
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val role = enumeration<UserRole>("role")
}
