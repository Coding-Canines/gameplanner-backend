package com.codingcanines.plugins

import com.codingcanines.database.users.ExposedUserRepository
import com.codingcanines.database.users.Users
import com.codingcanines.repositories.users.UserRepository
import io.ktor.server.application.*
import io.ktor.server.plugins.di.*
import kotlinx.coroutines.launch
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.SchemaUtils
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

fun Application.configureDependencies() {
    dependencies {
        val database = R2dbcDatabase.connect(
            url = System.getenv("DB_URL") ?: "r2dbc:postgresql://localhost:5432/ktor_db",
            user = System.getenv("DB_USER") ?: "ktor_user",
            password = System.getenv("DB_PASSWORD") ?: "ktor_password"
        )

        provide<R2dbcDatabase> { database }

        provide<UserRepository> { ExposedUserRepository(database = resolve<R2dbcDatabase>()) }

        launch {
            suspendTransaction(database) {
                SchemaUtils.create(Users)
            }
        }
    }
}
