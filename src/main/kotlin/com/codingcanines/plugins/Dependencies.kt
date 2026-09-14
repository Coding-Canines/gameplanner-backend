package com.codingcanines.plugins

import com.codingcanines.database.users.ExposedUserRepository
import com.codingcanines.repositories.users.UserRepository
import io.ktor.server.application.*
import io.ktor.server.plugins.di.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

fun Application.configureDependencies() {
    dependencies {
        val r2dbcUrl = System.getenv("DB_URL") ?: "r2dbc:postgresql://localhost:5432/ktor_db"
        val dbUser = System.getenv("DB_USER") ?: "ktor_user"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "ktor_password"

        val jdbcUrl = r2dbcUrl.replace("r2dbc", "jdbc")
        Flyway.configure()
            .dataSource(jdbcUrl, dbUser, dbPassword)
            .load()
            .migrate()

        val database = R2dbcDatabase.connect(url = r2dbcUrl, user = dbUser, password = dbPassword)

        provide<R2dbcDatabase> { database }

        provide<UserRepository> { ExposedUserRepository(database = resolve<R2dbcDatabase>()) }
    }
}
