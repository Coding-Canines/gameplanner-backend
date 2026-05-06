package com.codingcanines.database

import com.codingcanines.models.User
import com.codingcanines.models.UserRole
import com.codingcanines.repositories.UserRepository
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.r2dbc.*
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

class ExposedUserRepository(private val database: R2dbcDatabase) : UserRepository {
    override suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll().map { it.toUser() }.toList()
    }

    override suspend fun findByUsernameOrEmail(username: String, email: String): User? = dbQuery {
        Users.selectAll().where { (Users.username eq username) or (Users.email eq email) }.singleOrNull()?.toUser()
    }

    override suspend fun findByUsername(username: String): User? = dbQuery {
        Users.selectAll().where { Users.username eq username }.singleOrNull()?.toUser()
    }

    override suspend fun addUser(username: String, email: String, passwordHash: String): User = dbQuery {
        val insertedId = Users.insertAndGetId {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.passwordHash] = passwordHash
            it[Users.role] = UserRole.Staff
        }

        User(
            id = insertedId.value,
            username = username,
            email = email,
            passwordHash = passwordHash,
            role = UserRole.Staff
        )
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        suspendTransaction(db = database) { block() }

    private fun ResultRow.toUser(): User = User(
        id = this[Users.id].value,
        username = this[Users.username],
        email = this[Users.email],
        passwordHash = this[Users.passwordHash],
        role = this[Users.role]
    )
}
