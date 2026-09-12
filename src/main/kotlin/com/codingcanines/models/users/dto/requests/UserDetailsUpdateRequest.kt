package com.codingcanines.models.users.dto.requests

import com.codingcanines.models.users.UserDetails
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsUpdateRequest(
    val username: String,
    val fullLegalName: String? = null,
    val fullBirthName: String? = null,
    val mothersMaidenName: String? = null,
    val dateOfBirth: LocalDate? = null,
    val placeOfBirth: String? = null
)

fun UserDetailsUpdateRequest.toUserDetails(): UserDetails = UserDetails(
    username = username,
    fullLegalName = fullLegalName,
    fullBirthName = fullBirthName,
    mothersMaidenName = mothersMaidenName,
    dateOfBirth = dateOfBirth,
    placeOfBirth = placeOfBirth
)
