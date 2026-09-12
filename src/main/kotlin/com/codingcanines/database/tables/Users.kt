package com.codingcanines.database.tables

import com.codingcanines.models.users.UserRole
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.date

object Users : IntIdTable("users") {
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val role = enumeration<UserRole>("role")
    val fullLegalName = varchar("full_legal_name", 255).nullable()
    val fullBirthName = varchar("full_birth_name", 255).nullable()
    val mothersMaidenName = varchar("mothers_maiden_name", 255).nullable()
    val dateOfBirth = date("date_of_birth").nullable()
    val placeOfBirth = varchar("place_of_birth", 50).nullable()
}