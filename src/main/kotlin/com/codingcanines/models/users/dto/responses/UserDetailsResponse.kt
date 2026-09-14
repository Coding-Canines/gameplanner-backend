package com.codingcanines.models.users.dto.responses

import com.codingcanines.models.users.User
import com.codingcanines.models.users.UserRole
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsResponse(
    val id: Int,
    val username: String,
    val email: String,
    val role: UserRole,
    val fullLegalName: String?,
    val fullBirthName: String?,
    val mothersMaidenName: String?,
    val dateOfBirth: LocalDate?,
    val placeOfBirth: String?
)

fun User.toUserDetailsResponse(): UserDetailsResponse = UserDetailsResponse(
    id = id,
    username = username,
    email = email,
    role = role,
    fullLegalName = fullLegalName,
    fullBirthName = fullBirthName,
    mothersMaidenName = mothersMaidenName,
    dateOfBirth = dateOfBirth,
    placeOfBirth = placeOfBirth
)
