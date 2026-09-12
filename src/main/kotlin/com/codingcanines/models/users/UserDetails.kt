package com.codingcanines.models.users

import kotlinx.datetime.LocalDate

data class UserDetails(
    val username: String,
    val fullLegalName: String? = null,
    val fullBirthName: String? = null,
    val mothersMaidenName: String? = null,
    val dateOfBirth: LocalDate? = null,
    val placeOfBirth: String? = null
)
