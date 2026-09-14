package com.codingcanines.models.users.dto.requests

import com.codingcanines.models.users.UserDetails
import com.codingcanines.utils.validName
import io.konform.validation.Validation
import io.konform.validation.constraints.pattern
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable
import kotlin.time.Clock

@Serializable
data class UserDetailsUpdateRequest(
    val username: String,
    val fullLegalName: String? = null,
    val fullBirthName: String? = null,
    val mothersMaidenName: String? = null,
    val dateOfBirth: LocalDate? = null,
    val placeOfBirth: String? = null
) {
    companion object {
        val validator = Validation {
            UserDetailsUpdateRequest::username {
                validName(max = 50)
                pattern(Regex("^[a-zA-Z0-9_]+$")) hint "Username can only contain letters, numbers, and underscores"
            }

            UserDetailsUpdateRequest::fullLegalName ifPresent { validName() }

            UserDetailsUpdateRequest::fullBirthName ifPresent { validName() }

            UserDetailsUpdateRequest::mothersMaidenName ifPresent { validName() }

            UserDetailsUpdateRequest::placeOfBirth ifPresent { validName(min = 1, max = 50) }

            UserDetailsUpdateRequest::dateOfBirth ifPresent {
                constrain("Date of birth cannot be in the future") { date ->
                    date <= Clock.System.todayIn(TimeZone.currentSystemDefault())
                }
            }
        }
    }

    fun validate() = validator(this)
}

fun UserDetailsUpdateRequest.toUserDetails(): UserDetails = UserDetails(
    username = username,
    fullLegalName = fullLegalName,
    fullBirthName = fullBirthName,
    mothersMaidenName = mothersMaidenName,
    dateOfBirth = dateOfBirth,
    placeOfBirth = placeOfBirth
)
