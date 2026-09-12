package com.codingcanines.models.users

import kotlinx.datetime.LocalDate

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
    val role: UserRole,
    val fullLegalName: String? = null,
    val fullBirthName: String? = null,
    val mothersMaidenName: String? = null,
    val dateOfBirth: LocalDate? = null,
    val placeOfBirth: String? = null
)